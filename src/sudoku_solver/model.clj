(ns sudoku-solver.model
  (:require [sudoku-solver.utils :refer :all]))

; Sudoku puzzle is a grid of 81 squares.
; Collection of nine squares (row, col or box) is a unit.
; Squares that share a unit a called peers.
; Each square has 3 units and 20 peers.

; A1 A2 A3 │ A4 A5 A6 │ A7 A8 A9
; B1 B2 B3 │ B4 B5 B6 │ B7 B8 B9
; C1 C2 C3 │ C4 C5 C6 │ C7 C8 C9
; ─────────┼──────────┼──────────
; D1 D2 D3 │ D4 D5 D6 │ D7 D8 D9
; E1 E2 E3 │ E4 E5 E6 │ E7 E8 E9
; F1 F2 F3 │ F4 F5 F6 │ F7 F8 F9
; ─────────┼──────────┼──────────
; G1 G2 G3 │ G4 G5 G6 │ G7 G8 G9
; H1 H2 H3 │ H4 H5 H6 │ H7 H8 H9
; I1 I2 I3 │ I4 I5 I6 │ I7 I8 I9

(defn cross
  "Cross-product of two seqs"
  [s1 s2]
  (for [x s1
        y s2]
    (str x y)))

(def rows "ABCDEFGHI")

(def cols "123456789")

(def squares
  "Every square on the grid"
  (cross rows cols))

(def unitlist
  "Every row, col and box of the grid"
  (concat (map #(cross rows [%]) cols)
          (map #(cross [%] cols) rows)
          (map #(apply cross %)
               (for [r (partition 3 rows)
                     c (partition 3 cols)]
                 [r c]))))

(def units
  "Map of units of the squares"
  (into {} (map (fn [square]
                  (vector square
                          (filter #(some #{square} %)
                                  unitlist)))
                squares)))

(def peers
  "Map of peers of the squares"
  (into {} (map (fn [square]
                  (vector square
                          (disj
                            (set (apply concat
                                        (units square)))
                            square)))
                squares)))

; Textual representation of the puzzle is a grid
; Data structure that representns game state will be called values.

(def starting-grid
  (str "5 3 . │ . 7 . │ . . ."
       "6 . . │ 1 9 5 │ . . ."
       ". 9 8 │ . . . │ . 6 ."
       "──────┼───────┼──────"
       "8 . . │ . 6 . │ . . 3"
       "4 . . │ 8 . 3 │ . . 1"
       "7 . . │ . 2 . │ . . 6"
       "──────┼───────┼──────"
       ". 6 . │ . . . │ 2 8 ."
       ". . . │ 4 1 9 │ . . 5"
       ". . . │ . 8 . │ . 7 9"))

(def solved-grid
  (str "5 3 4 │ 6 7 8 │ 9 1 2"
       "6 7 2 │ 1 9 5 │ 3 4 8"
       "1 9 8 │ 3 4 2 │ 5 6 7"
       "──────┼───────┼──────"
       "8 5 9 │ 7 6 1 │ 4 2 3"
       "4 2 6 │ 8 5 3 │ 7 9 1"
       "7 1 3 │ 9 2 4 │ 8 5 6"
       "──────┼───────┼──────"
       "9 6 1 │ 5 3 7 │ 2 8 4"
       "2 8 7 │ 4 1 9 │ 6 3 5"
       "3 4 5 │ 2 8 6 │ 1 7 9"))

(defn grid-values
  [grid]
  {:post [(= (count %) 81)]}
  "Convert grid to a mapping of squares to values.
  Dots and zeros represent emtpy squares."
  (->> grid
       (filter (set (str cols \. \0)))
       (zipmap squares)))

(defn parse-grid
  [grid]
  "Convert grid to a mapping of squares to collection of possible values
  or return false if contradiction is detected."
  (into {} (map (fn [[k v]]
                  (if (or (= v \.) (= v \0))
                    [k cols]
                    [k (str v)]))
                (grid-values grid))))

(defn display
  [values]
  (let [width (apply max (map count (vals values)))
        box-width (+ 5 (* 3 width))
        line (clojure.string/join "┼" (repeat 3 (str-repeat box-width "─")))]
    (doseq [r rows]
      (doseq [c cols]
        (print (str-center width (values (str r c)))
               (if (or (= c \3) (= c \6)) "│" " ")))
      (println)
      (when (or (= r \C) (= r \F))
        (println line)))))

