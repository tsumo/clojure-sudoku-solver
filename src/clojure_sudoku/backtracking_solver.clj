(ns clojure-sudoku.backtracking-solver
  (:require [clojure-sudoku.model :refer :all]))


(defn solve
  [grid]
  (if (grid-solved? grid)
    grid
    ))

