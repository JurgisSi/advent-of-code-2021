(ns aoc2021.day2.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-command
  [line]
  (let [parts (str/split line #" ")]
    (hash-map :direction (first parts), :distance (Integer/parseInt (second parts)))))

(defn count-horizontal [command horizontal]
  (case (get command :direction)
    "forward" (+ horizontal (get command :distance))
    horizontal)
  )

(defn count-vertical [command vertical]
  (case (get command :direction)
    "down" (+ vertical (get command :distance))
    "up" (- vertical (get command :distance))
    vertical)
  )

(defn calculate-position
  ([path] (calculate-position (map create-command (str/split-lines (slurp path))) 0 0))
  ([commands horizontal vertical]
   (if (empty? commands)
     (* horizontal vertical)
     (let [command (first commands)]
       (calculate-position (rest commands) (count-horizontal command horizontal) (count-vertical command vertical))))))

(println (calculate-position input-path))
