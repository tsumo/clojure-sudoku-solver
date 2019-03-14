(ns clojure-sudoku.model)


(defn prepare-board
  [grid]
  (mapv (fn [row]
          (mapv #(if (nil? %)
                   (vector nil false)
                   (vector % true))
                row))
        grid))


(def starting-grid
  (prepare-board
    [[5   3   nil nil 7   nil nil nil nil]
     [6   nil nil 1   9   5   nil nil nil]
     [nil 9   8   nil nil nil nil 6   nil]
     [8   nil nil nil 6   nil nil nil 3]
     [4   nil nil 8   nil 3   nil nil 1]
     [7   nil nil nil 2   nil nil nil 6]
     [nil 6   nil nil nil nil 2   8   nil]
     [nil nil nil 4   1   9   nil nil 5]
     [nil nil nil nil 8   nil nil 7   9]]))


(def solved-grid
  (prepare-board
    [[5 3 4 6 7 8 9 1 2]
     [6 7 2 1 9 5 3 4 8]
     [1 9 8 3 4 2 5 6 7]
     [8 5 9 7 6 1 4 2 3]
     [4 2 6 8 5 3 7 9 1]
     [7 1 3 9 2 4 8 5 6]
     [9 6 1 5 3 7 2 8 4]
     [2 8 7 4 1 9 6 3 5]
     [3 4 5 2 8 6 1 7 9]]))


(defn gen-grid
  "Generates sudoku grid"
  []
  (repeat 9 (repeat 9 (vector nil false))))


(defn get-row
  "Gets single grid row"
  [n grid]
  (nth grid n))


(defn get-col
  "Gets single grid column"
  [n grid]
  (mapv #(nth % n) grid))


(defn get-box
  "Gets single grid box"
  [n grid]
  (apply concat
         (mapv #(take 3 (nthnext % (* 3 (mod n 3))))
               (take 3 (nthnext grid (* 3 (quot n 3)))))))


(defn get-all-groups
  "Gets every row, column and box of the grid"
  [grid]
  (concat (concat (map #(get-row % grid) (range 9)))
          (concat (map #(get-col % grid) (range 9)))
          (concat (map #(get-box % grid) (range 9)))))


(defn get-cell-val
  "Numerical value of the cell"
  [cell]
  (first cell))


(defn cell-solved?
  "Is this cell value determined unambiguously?"
  [cell]
  (second cell))


(defn group-solved?
  "Is this group solved?"
  [group]
  (every? identity
          (map (fn [n] (some #(= n (get-cell-val %)) group))
               (range 1 10))))


(defn grid-solved?
  "Is this grid solved?"
  [grid]
  (every? identity
          (map group-solved? (get-all-groups grid))))


(defn set-cell
  "Updates cell contents using row and col numbers"
  [[row col] [value final] grid]
  (assoc-in grid [row col] [value final]))


;;; Printing


(defn get-cell-string
  "Printable representation of a single cell"
  [[value _]]
  (if (= value nil) \. value))


(defn get-row-string
  "Printable representation of a single row"
  [row]
  (clojure.string/join
    " │ "
    (map #(clojure.string/join \space %)
         (partition 3 (map get-cell-string row)))))


(defn get-grid-string
  "Printable representation of a whole grid"
  [grid]
  (clojure.string/join
    "\n──────┼───────┼──────\n"
    (map #(clojure.string/join \newline %)
         (partition 3 (map get-row-string grid)))))

