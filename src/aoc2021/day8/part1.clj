(ns aoc2021.day8.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-input
  [path]
  (let [lines (map #(str/split % #" \| ") (str/split-lines (slurp path)))]
    (map #(hash-map :tries (str/split (first %) #" ") :output (str/split (last %) #" ")) lines))
  )

(defn is-unique-length
  [segment]
  (let [length (count segment)]
    (or (= length 2) (= length 3) (= length 4) (= length 7)))
  )

(defn count-1-4-7-8
  [input]
  (count (filter #(is-unique-length %) (flatten (map #(get % :output) input))))
  )

(println (count-1-4-7-8 (create-input input-path)))
