(ns prime-factors.core)

(defn factors-of [n]
  (prn "factoring " n)
  (loop [factors [] n n divisor 2]
    (if (> n 1)
      (if (= 0 (mod n divisor))
        (do
          (prn divisor)
          (recur (conj factors divisor)
                 (quot n divisor)
                 divisor))
        (do
          (when (= 0 (mod divisor 100000000))
            (prn "passing " divisor))
          (recur factors n (inc divisor))))
      factors)))
