(ns aoc2021.day10.part1
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(def braces (hash-map "(" :open, "[" :open, "{" :open, "<" :open, ")" :close, "]" :close, "}" :close, ">" :close))
(def pairs (hash-map "(" ")", "[" "]", "{" "}", "<" ">"))
(def points (hash-map ")" 3, "]" 57, "}" 1197, ">" 25137))

(defn parse-input [path]
  (map #(str/split % #"") (str/split-lines (slurp path)))
  )

(defn remove-idx [i items]
  (keep-indexed #(when-not (= i %1) %2) items))

(defn resolve-points
  ([row] (apply resolve-points row row))
  ([full one two & rest]
   (let [first-brace (get braces one)
         second-brace (get braces two)]
     (if (and (= first-brace :open) (= second-brace :close))
       (if (= (get pairs one) two)
         (let [index (- (count full) (count rest) 2)
               newFull (remove-idx index (remove-idx index full))]
           (apply resolve-points newFull newFull))
         (get points two)
         )
       (apply resolve-points full two rest)
       )
     )
   )
  ([full one two]
   (let [first-brace (get braces one)
         second-brace (get braces two)]
     (if (and (= first-brace :open) (= second-brace :close) (not= (get pairs one) two))
       (get points two)
       0
       )
     )
   )
  )

(println (reduce + (map #(resolve-points %) (parse-input input-path))))
