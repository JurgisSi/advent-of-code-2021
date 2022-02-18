(ns aoc2021.day1.main
  (:require [clojure.string :as str])
  )

(def input-path "input.txt")

(defn inc-if-increase
  [second first increases]
  (if (> second first) (inc increases) increases)
  )

(defn count-increases
  ([path] (count-increases (map #(Integer/parseInt %) (str/split-lines (slurp path))) 0))
  ([numbers increases]
   (if (< (count numbers) 2)
     increases
     (count-increases (rest numbers) (inc-if-increase (second numbers) (first numbers) increases)))))

(defn count-sliding-increases
  ([path] (count-sliding-increases (vec (map #(Integer/parseInt %) (str/split-lines (slurp path)))) 0))
  ([numbers increases]
   (if (< (count numbers) 4)
     increases
     (count-sliding-increases (vec (rest numbers)) (inc-if-increase (reduce + (subvec numbers 1 4)) (reduce + (subvec numbers 0 3)) increases)))))

(println (count-increases input-path))
(println (count-sliding-increases input-path))
