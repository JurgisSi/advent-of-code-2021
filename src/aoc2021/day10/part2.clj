(ns aoc2021.day10.part2
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(def braces (hash-map "(" :open, "[" :open, "{" :open, "<" :open, ")" :close, "]" :close, "}" :close, ">" :close))
(def pairs (hash-map "(" ")", "[" "]", "{" "}", "<" ">"))
(def points (hash-map "(" 1 "[" 2 "{" 3 "<" 4))

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

(defn simplify
  ([row] (simplify row row))
  ([full other]
   (if (empty? other)
     full
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
           full
           )
         (recur full (rest other))
         )
       )
     )
   )
  )

(defn is-valid? [row]
  (not (or (contains? (set row) ")") (contains? (set row) "]") (contains? (set row) "}") (contains? (set row) ">")))
  )

(defn convert [braces]
  (map #(get points %) (reverse braces))
  )

(defn calculate-score
  ([weights] (calculate-score 0 weights))
  ([score weights]
   (if (empty? weights)
     score
     (calculate-score (+ (* score 5) (first weights)) (rest weights))
     )
   )
  )

(defn find-winner [results]
  (nth (sort results) (/ (count results) 2))
  )

(println (find-winner (map #(calculate-score %) (map #(convert %) (filter #(is-valid? %) (map #(simplify %) (parse-input input-path)))))))
