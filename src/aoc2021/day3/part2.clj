(ns aoc2021.day3.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn count-ones
  [bits]
  (count (filter #(= (str %) "1") bits))
  )

(defn find-target [input index half comparison default]
  (let [bits (map #(nth % index) input)
        one-count (count-ones bits)]
    (if (= one-count half)
      default
      (if (comparison one-count half)
        "1"
        "0"
        )))
  )

(defn find-rating
  [input index comparison default]
  (if (<= (count input) 1)
    (first input)
    (let [half (/ (count input) 2)
          target (find-target input index half comparison default)]
      (find-rating (filter #(= target (str (nth % index))) input) (inc index) comparison default))
    )
  )

(defn find-oxygen-generator-rating
  [input] (find-rating input 0 > "1")
  )

(defn find-co2-scrubber-rating
  [input] (find-rating input 0 < "0")
  )

(defn calculate-power-consumption
  [path]
  (let [input (str/split-lines (slurp path))
        oxygen-generator-rating (find-oxygen-generator-rating input)
        co2-scrubber-rating (find-co2-scrubber-rating input)]
    (* (Integer/parseInt oxygen-generator-rating 2) (Integer/parseInt co2-scrubber-rating 2))
    )
  )

(println (calculate-power-consumption input-path))
