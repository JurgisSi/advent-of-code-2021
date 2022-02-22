(ns aoc2021.day4.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn create-moves [line]
  (str/split line #",")
  )

(defn create-boards
  ([lines] (create-boards (map #(str/split (str/trim %) #"\s+") (filter #(not (empty? %)) lines)) '()))
  ([rows boards]
   (if (empty? rows)
     boards
     (create-boards (drop 5 rows) (conj boards (take 5 rows)))))
  )

(defn row-marked? [row played-moves]
  (every? (set played-moves) row)
  )

(defn test-rows
  [board played-moves]
  (map #(row-marked? % played-moves) board)
  )

(defn test-columns
  [board played-moves]
  (map #(row-marked? % played-moves) (apply map list board))
  )

(defn is-winner?
  [board played-moves]
  (some true? (concat (test-rows board played-moves) (test-columns board played-moves)))
  )

(defn play-bingo
  ([path]
   (let [input (str/split-lines (slurp path))
         moves (create-moves (first input))
         boards (create-boards (rest input))]
     (play-bingo moves boards '[])))
  ([moves boards played-moves]
   (if (<= (count boards) 1)
     (list (first boards) played-moves)
     (play-bingo (rest moves) (filter #(not (is-winner? % played-moves)) boards) (conj played-moves (first moves)))
     )
   )
  )

(defn calculate-score
  [data]
  (let [[winner played-moves] data]
    (* (reduce + (map #(Integer/parseInt %) (filter #(not (some #{%} played-moves)) (flatten winner)))) (Integer/parseInt (last played-moves))))
  )

(println (calculate-score (play-bingo input-path)))
