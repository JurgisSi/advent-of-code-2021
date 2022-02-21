(ns aoc2021.day3.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn count-ones
  [bits]
  (count (filter #(= (str %) "1") (vec bits)))
  )

(defn find-most-common
  [input index]
  (let [bits (map #(nth % index) input)]
    (if (> (count-ones bits) (/ (count input) 2))
      "1"
      "0"
      ))
  )

(defn find-gamma-rate
  ([input] (find-gamma-rate input "" 0))
  ([input gamma-rate index]
   (if (>= index (count (first input)))
     gamma-rate
      (find-gamma-rate input (str gamma-rate (find-most-common input index)) (inc index))
     ))
  )

(defn invert
  [bit]
  (if (= bit "1")
    "0"
    "1"))

(defn find-epsilon-rate
  [gamma-rate]
  (str/join (map invert (str/split gamma-rate #"")))
  )

(defn calculate-power-consumption
  [path]
  (let [input (str/split-lines (slurp path))
        gamma-rate (find-gamma-rate input)
        epsilon-rate (find-epsilon-rate gamma-rate)]
    (* (Integer/parseInt gamma-rate 2) (Integer/parseInt epsilon-rate 2))
    )
  )

(println (calculate-power-consumption input-path))
