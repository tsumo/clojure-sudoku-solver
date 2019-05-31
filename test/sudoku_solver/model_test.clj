(ns sudoku-solver.model-test
  (:require [clojure.test :refer :all]
            [sudoku-solver.model :refer :all]))

(deftest model-test

  (testing "Testing model"

    (is (= (count squares) 81))

    (is (= (count unitlist) 27))

    (is (every? #(= (count (units %)) 3) squares))

    (is (every? #(= (count (peers %)) 20) squares))

    (is (= (units "C2")
           [["A2" "B2" "C2" "D2" "E2" "F2" "G2" "H2" "I2"]
            ["C1" "C2" "C3" "C4" "C5" "C6" "C7" "C8" "C9"]
            ["A1" "A2" "A3" "B1" "B2" "B3" "C1" "C2" "C3"]]))

    (is (= (peers "C2")
           (set ["A2" "B2" "D2" "E2" "F2" "G2" "H2" "I2"
                 "C1" "C3" "C4" "C5" "C6" "C7" "C8" "C9"
                 "A1" "A3" "B1" "B3"])))))

