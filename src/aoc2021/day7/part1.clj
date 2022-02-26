(ns aoc2021.day7.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-input
  [path]
  (map #(Integer/parseInt %) (str/split (slurp path) #","))
  )

(defn find-median
  [crabs]
  (let [half (Math/floor (/ (count crabs) 2))
        sorted (sort crabs)]
    (if (even? (count crabs))
      (list (nth sorted half) (nth sorted (dec half)))
      (list (nth sorted half))))
  )

(defn find-distance
  [median crabs]
  (apply + (map #(Math/abs ^long (- % median)) crabs))
  )

(defn find-optimal-distance
  [crabs]
  (let [median (find-median crabs)]
    (apply min (map #(find-distance % crabs) median))
    )
  )

(println (find-optimal-distance (create-input input-path)))
