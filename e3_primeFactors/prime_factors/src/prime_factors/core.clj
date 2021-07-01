(ns prime-factors.core)

(defn factors-of [n]
  (if (> n 1)
    (if (= 0 (mod n 2))
      (concat [2] (factors-of (quot n 2)))
      [n])
    []))
