(ns aoc2021.day5.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-coordinates
  [row]
  (let [[start end] (str/split row #" -> ")
        startCoords (str/split start #",")
        endCoords (str/split end #",")]
    (hash-map :x1 (Integer/parseInt (first startCoords)) :x2 (Integer/parseInt (first endCoords)) :y1 (Integer/parseInt (last startCoords)) :y2 (Integer/parseInt (last endCoords))))
  )

(defn create-input
  [path]
  (map #(create-coordinates %) (str/split-lines (slurp path)))
  )

(defn resolve-increment [range]
  (cond
    (< range 0) -1
    (> range 0) 1
    :else 0
    )
  )

(defn create-point
  [x y]
  (str x "," y)
  )

(defn create-coords
  ([pipe]
   (let [x1 (get pipe :x1)
         x2 (get pipe :x2)
         y1 (get pipe :y1)
         y2 (get pipe :y2)
         x-range (- x2 x1)
         y-range (- y2 y1)
         ]
     (create-coords x1 x2 y1 y2 (resolve-increment x-range) (resolve-increment y-range) '())
     ))
  ([x1 x2 y1 y2 inc1 inc2 coords]
   (if (and (= x1 x2) (= y1 y2))
     (conj coords (create-point x1 y1))
     (create-coords (+ x1 inc1) x2 (+ y1 inc2) y2 inc1 inc2 (conj coords (create-point x1 y1))))
   )
  )

(defn find-coordinates
  [pipes]
  (flatten (map #(create-coords %) pipes))
  )

(defn duplicates
  [used-coordinates]
  (for [[id freq] (frequencies used-coordinates)
        :when (> freq 1)]
    id))

(defn is-line?
  [pipe]
  (or (= (get pipe :x1) (get pipe :x2)) (= (get pipe :y1) (get pipe :y2)))
  )

(defn calculate-overlays
  ([pipes]
   (let [used-coordinates (find-coordinates (filter #(is-line? %) pipes))]
     (count (duplicates used-coordinates))
     ))
  )

(println (calculate-overlays (create-input input-path)))
