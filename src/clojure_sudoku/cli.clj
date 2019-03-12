(ns clojure-sudoku.cli)


(defn get-cell-string
  [cell]
  (if (= cell nil) \space cell))


(defn get-row-string
  [row]
  (clojure.string/join \space (map get-cell-string row)))


(defn get-grid-string
  [grid]
  (clojure.string/join \newline (map get-row-string grid)))

