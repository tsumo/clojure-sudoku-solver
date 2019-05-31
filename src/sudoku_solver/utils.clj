(ns sudoku-solver.utils)

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

