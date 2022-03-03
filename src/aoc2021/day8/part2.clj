(ns aoc2021.day8.part2
  (:require [clojure.string :as str]
            clojure.set)
  )

(def input-path "input.txt")

(defn create-input
  [path]
  (let [lines (map #(str/split % #" \| ") (str/split-lines (slurp path)))]
    (map #(hash-map :tries (str/split (first %) #" ") :output (str/split (last %) #" ")) lines))
  )

(defn sort-segment
  [try]
  (apply str (sort (str/split try #"")))
  )

(defn sort-segments
  [tries]
  (map #(sort-segment %) tries)
  )

(defn find-one [tries]
  (first (filter #(= (count %) 2) tries))
  )

(defn find-four [tries]
  (first (filter #(= (count %) 4) tries))
  )

(defn find-seven [tries]
  (first (filter #(= (count %) 3) tries))
  )

(defn find-eight [tries]
  (first (filter #(= (count %) 7) tries))
  )

(defn find-three
  [tries seven]
  (first (filter #(and (= (count %) 5) (clojure.set/subset? (set (str/split seven #"")) (set (str/split % #"")))) tries))
  )

(defn find-nine [tries four]
  (first (filter #(and (= (count %) 6) (clojure.set/subset? (set (str/split four #"")) (set (str/split % #"")))) tries))
  )

(defn find-zero [tries seven nine]
  (first (filter #(and (= (count %) 6) (not= % nine) (clojure.set/subset? (set (str/split seven #"")) (set (str/split % #"")))) tries))
  )

(defn find-six [tries nine zero]
  (first (filter #(and (= (count %) 6) (not= % nine) (not= % zero)) tries))
  )

(defn find-five [tries six]
  (first (filter #(and (= (count %) 5) (= (count (clojure.set/difference (set (str/split six #"")) (set (str/split % #"")))) 1)) tries))
  )

(defn find-two [tries three five]
  (first (filter #(and (= (count %) 5) (not= % three) (not= % five)) tries))
  )

(defn decode
  [input]
  (let [sorted-tries (sort-segments (get input :tries))
        one (find-one sorted-tries)
        four (find-four sorted-tries)
        seven (find-seven sorted-tries)
        eight (find-eight sorted-tries)
        three (find-three sorted-tries seven)
        nine (find-nine sorted-tries four)
        zero (find-zero sorted-tries seven nine)
        six (find-six sorted-tries nine zero)
        five (find-five sorted-tries six)
        two (find-two sorted-tries three five)
        mapping (hash-map one 1 two 2 three 3 four 4 five 5 six 6 seven 7 eight 8 nine 9 zero 0)]
    (Integer/parseInt (apply str (map #(get mapping %) (sort-segments (get input :output))))))
  )

(defn sum-codes
  [inputs]
  (apply + (map #(decode %) inputs))
  )

(println (sum-codes (create-input input-path)))
