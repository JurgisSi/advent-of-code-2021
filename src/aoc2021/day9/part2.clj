(ns aoc2021.day9.part2
  (:require [clojure.string :as str]
            clojure.set)
  )

(def input-path "input.txt")

(defn split [row]
  (map #(Integer/parseInt %) (str/split row #""))
  )

(defn create-input [path]
  (map #(split %) (str/split-lines (slurp path)))
  )

(defn fetch-point-or-max [x y input]
  (if (and (>= x 0) (>= y 0) (< y (count input)) (< x (count (nth input y))))
    (nth (nth input y) x)
    9)
  )

(defn create-coordinates [input]
  (for [y (range (count input))
        x (range (count (nth input y)))]
    (hash-map :x x :y y)
    )
  )

(defn find-basin
  ([input x y] (find-basin input x y #{}))
  ([input x y basins]
   (let [point (fetch-point-or-max x y input)
         xy (str x y)]
     (if (or (= 9 point) (contains? basins xy))
       basins
       (let [up (find-basin input x (dec y) (conj basins xy))
             right (find-basin input (inc x) y (clojure.set/union basins up))
             down (find-basin input x (inc y) (clojure.set/union basins right))
             left (find-basin input (dec x) y (clojure.set/union basins down))]
         left)
       )
     )
   )
  )

(defn find-basins
  ([input] (apply find-basins input '() #{} (create-coordinates input)))
  ([input basins visited coordinate]
   (let [x (get coordinate :x)
         y (get coordinate :y)
         xy (str x y)
         point (fetch-point-or-max x y input)]
     (if (or (= 9 point) (contains? visited xy))
       basins
       (conj basins 1)
       )))
  ([input basins visited coordinate & coordinates]
   (println "coord:" coordinate)
   (println "coordinates:" (count coordinates))
   (let [x (get coordinate :x)
         y (get coordinate :y)
         xy (str x y)
         point (fetch-point-or-max x y input)]
     (if (or (= 9 point) (contains? visited xy))
       (apply find-basins input basins (conj visited xy) coordinates)
       (let [basin (find-basin input x y)]
         (println "basin:" basin)
         (apply find-basins input (conj basins (count basin)) (clojure.set/union visited basin) coordinates))
       )
     )
   )
  )

(println (apply * (take-last 3 (sort (find-basins (create-input input-path))))))
