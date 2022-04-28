(ns aoc2021.day10.part1-test
  (:require [clojure.test :refer :all]
            [aoc2021.day10.part1 :refer :all]))

(deftest basic-tests
  (is (= (resolve-points (clojure.string/split "[({(<(())[]>[[{[]{<()<>>" #"")) 0))
  (is (= (resolve-points (clojure.string/split "[(()[<>])]({[<{<<[]>>(" #"")) 0))
  (is (= (resolve-points (clojure.string/split "{([(<{}[<>[]}>{[]{[(<()>" #"")) 1197))
  (is (= (resolve-points (clojure.string/split "(((({<>}<{<{<>}{[]{[]{}" #"")) 0))
  (is (= (resolve-points (clojure.string/split "[[<[([]))<([[{}[[()]]]" #"")) 3))
  (is (= (resolve-points (clojure.string/split "[{[{({}]{}}([{[{{{}}([]" #"")) 57))
  (is (= (resolve-points (clojure.string/split "{<[[]]>}<{[{[{[]{()[[[]" #"")) 0))
  (is (= (resolve-points (clojure.string/split "[<(<(<(<{}))><([]([]()" #"")) 3))
  (is (= (resolve-points (clojure.string/split "<{([([[(<>()){}]>(<<{{" #"")) 25137))
  (is (= (resolve-points (clojure.string/split "<{([{{}}[<[[[<>{}]]]>[]]" #"")) 0))
  )
