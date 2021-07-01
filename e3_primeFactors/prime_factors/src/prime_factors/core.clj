(ns prime-factors.core)

(defn factors-of [n]
  (if (> n 1)
    (cond
      (= 0 (mod n 2))
      (concat [2] (factors-of (quot n 2)))
      (= 0 (mod n 3))
      (concat [3] (factors-of (quot n 3)))
      :else
      [n])
    []))
