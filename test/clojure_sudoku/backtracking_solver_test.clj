(ns clojure-sudoku.backtracking-solver-test
  (:require [clojure.test :refer :all]
            [clojure-sudoku.model :refer :all]
            [clojure-sudoku.backtracking-solver :refer :all]))

(deftest backtracking-solver-test

  (testing "Checking backtracking solver"
    (is (= (solve starting-grid) solved-grid))))

