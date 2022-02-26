(ns aoc2021.day7.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-input
  [path]
  (map #(Integer/parseInt %) (str/split (slurp path) #","))
  )

(defn find-average
  [crabs]
  (let [average (/ (reduce + crabs) (count crabs))]
    (list (Math/floor average) (Math/ceil average)))
  )

(defn count-cost
  [distance]
  (/ (* distance (inc distance)) 2)
  )

(defn find-distance
  [median crabs]
  (apply + (map #(count-cost (Math/abs ^long (- % median))) crabs))
  )

(defn find-optimal-distance
  [crabs]
  (let [average (find-average crabs)]
    (apply min (map #(find-distance % crabs) average))
    )
  )

(println (find-optimal-distance (create-input input-path)))
