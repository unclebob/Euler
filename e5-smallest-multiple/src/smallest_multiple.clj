(ns smallest-multiple)

(defn gcd [a b]
        (if (zero? b)
          a
          (recur b (mod a b))))

(defn factors-of [n]
  (loop [factors [] n n divisor 2]
    (if (> n 1)
      (cond
        (> divisor (Math/sqrt n))
        (conj factors n)
        (= 0 (mod n divisor))
        (recur (conj factors divisor)
               (quot n divisor)
               divisor)
        :else
        (recur factors n (inc divisor)))
      factors)))

(defn lcm [a b] (/ (* a b) (gcd a b)))

(defn smallest-multiple [ns]
  (reduce lcm 1 ns))