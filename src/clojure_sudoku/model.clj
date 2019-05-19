(ns clojure-sudoku.model)

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

(def rows [\A \B \C \D \E \F \G \H \I])

(def cols [\1 \2 \3 \4 \5 \6 \7 \8 \9])

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

(defn parse-grid
  [grid]
  "Convert grid to a mapping of squares to collection of possible values.
  Single value is given to the squares which are determined unambiguosly."
  (->> grid
       (filter (set (conj cols \.)))
       (map #(if (= % \.) (range 1 10) (Character/digit % 10)))
       (zipmap squares)))


; Printing

; (defn get-cell-string
;   "Printable representation of a single cell"
;   [[value _]]
;   (if (= value 0) \. value))
;
;
; (defn get-row-string
;   "Printable representation of a single row"
;   [row]
;   (clojure.string/join
;     " │ "
;     (map #(clojure.string/join \space %)
;          (partition 3 (map get-cell-string row)))))
;
;
; (defn get-grid-string
;   "Printable representation of a whole grid"
;   [grid]
;   (clojure.string/join
;     "\n──────┼───────┼──────\n"
;     (map #(clojure.string/join \newline %)
;          (partition 3 (map get-row-string grid)))))

