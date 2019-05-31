(ns sudoku-solver.utils)

(defn cross
  "Cross-product of two seqs"
  [s1 s2]
  (for [x s1
        y s2]
    (str x y)))

(defn str-repeat
  "Multiplies string n times"
  [n s]
  (apply str (repeat n s)))

(defn str-spaces
  "Create a string of n spaces."
  [n]
  (str-repeat n \space))

(defn str-center
  "Pads string to the left and right to required length"
  [len s]
  (let [slen (count s)
        lpad (int (/ (- len slen) 2))
        rpad (- len slen lpad)]
    (str (str-spaces lpad) s (str-spaces rpad))))

