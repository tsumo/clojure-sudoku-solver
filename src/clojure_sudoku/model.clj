(ns clojure-sudoku.model
  (:require [clojure-sudoku.cli :refer :all]))


(def sample-grid
  [[1   2   nil 4   5   nil 7   8   nil]
   [9   8   7   nil 5   nil 3   2   1]
   [1   2   3   4   5   6   7   8   9]
   [1   nil 2   nil 3   nil 4   nil 5]
   [1   2   nil 4   5   6   7   nil 9]
   [nil 8   nil 6   nil 4   nil 2   1]
   [1   nil 3   4   5   nil 7   8   9]
   [1   nil 2   nil 3   nil 4   nil 5]
   [1   2   3   nil 5   6   nil 8   9]])


(defn gen-grid
  []
  (repeat 9 (repeat 9 nil)))


(defn get-row
  [n grid]
  (nth grid n))


(defn get-col
  [n grid]
  (map #(nth % n) grid))


(defn get-box
  [n grid]
  (map #(take 3 (nthnext % (* 3 (mod n 3))))
       (take 3 (nthnext grid (* 3 (quot n 3))))))

