(ns prime-factors.core)

(defn factors-of [n]
  (loop [factors [] n n divisor 2]
    (if (> n 1)
      (if (= 0 (mod n divisor))
        (recur (conj factors divisor)
               (quot n divisor)
               divisor)
        (recur factors n (inc divisor)))
      factors)))
