(ns aoc2021.day2.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-command
  [line]
  (let [parts (str/split line #" ")]
    (hash-map :direction (first parts), :distance (Integer/parseInt (second parts)))))

(defn apply-command [command horizontal depth aim]
  (case (get command :direction)
    "forward" (list (+ horizontal (get command :distance)) (+ depth (* aim (get command :distance))) aim)
    "down" (list horizontal depth (+ aim (get command :distance)))
    "up" (list horizontal depth (- aim (get command :distance)))
    horizontal depth aim)
  )

(defn calculate-position
  ([path]
   (calculate-position (map create-command (str/split-lines (slurp path))) 0 0 0))
  ([commands horizontal depth aim]
   (if (empty? commands)
     (* horizontal depth)
     (let [command (first commands)
           [new-horizontal new-depth new-aim] (apply-command command horizontal depth aim)]
       (calculate-position (rest commands) new-horizontal new-depth new-aim)))))

(println (calculate-position input-path))
