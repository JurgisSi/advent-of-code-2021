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

(defn is-brace-pair? [first-brace second-brace]
  (and (= first-brace :open) (= second-brace :close))
  )

(defn brace-match? [one two]
  (= (get pairs one) two))

(defn resolve-points
  ([row] (resolve-points row row))
  ([full other]
   (if (empty? other)
     0
     (let [one (first other)
           two (second other)
           first-brace (get braces one)
           second-brace (get braces two)]
       (if (is-brace-pair? first-brace second-brace)
         (if (brace-match? one two)
           (let [index (- (count full) (count other))
                 newFull (remove-idx index (remove-idx index full))]
             (recur newFull newFull)
             )
           (get points two)
           )
         (recur full (rest other))
         )
       )
     )
   )
  )

(println (reduce + (map #(resolve-points %) (parse-input input-path))))
