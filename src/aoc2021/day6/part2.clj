(ns aoc2021.day6.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

;wolfram alpha found a pattern, generated fish count when starting with specific age
(def fish-after-256
  (hash-map "1" 6206821033 "2" 5617089148 "3" 5217223242 "4" 4726100874 "5" 4368232009 "6" 3989468462))

(defn count-lanternfish
  [path]
  (apply + (map #(get fish-after-256 %) (str/split (slurp path) #",")))
  )

(println (count-lanternfish input-path))
