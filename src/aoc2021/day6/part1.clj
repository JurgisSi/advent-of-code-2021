(ns aoc2021.day6.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-lanternfish
  [path]
  (map #(Integer/parseInt %) (str/split (slurp path) #","))
  )

(defn handle-negatives
  [lanternfish]
  (if (< lanternfish 0)
    6
    lanternfish)
  )

(defn simulate-day
  [lanternfish]
  (let [new-lanternfish-count (count (filter #(<= % 0) lanternfish))]
    (concat (map #(handle-negatives %) (map dec lanternfish)) (repeat new-lanternfish-count 8)))
  )

(defn simulate
  [lanternfish days]
  (if (= days 0)
    lanternfish
    (simulate (simulate-day lanternfish) (dec days)))
  )

(println (count (simulate (create-lanternfish input-path) 80)))
