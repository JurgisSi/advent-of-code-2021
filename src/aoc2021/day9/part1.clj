(ns aoc2021.day9.part1
  (:require [clojure.string :as str])
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

(defn is-lowest? [x y input]
  (let [point (fetch-point-or-max x y input)]
    (and (< point (fetch-point-or-max (dec x) y input))
         (< point (fetch-point-or-max x (dec y) input))
         (< point (fetch-point-or-max (inc x) y input))
         (< point (fetch-point-or-max x (inc y) input)))
    )
  )

(defn count-risk-level [input]
  (let [risk-level (atom 0)]
    (doseq [y (range (count input))
            x (range (count (nth input y)))]
      (when (is-lowest? x y input)
        (reset! risk-level (+ @risk-level (inc (fetch-point-or-max x y input)))))
      )
    @risk-level)
  )

(println (count-risk-level (create-input input-path)))
