(ns e7-1001st-prime.core
  (:import (sieve Sieve)))

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

(defn fast-primes-up-to [n]
  (vec (Sieve/primesUpTo n)))

(defn mark-multiples [composites prime n]
  (loop [i (* 2 prime)]
    (when (<= i n)
      (aset composites i true)
      (recur (+ i prime))))
  )

(defn filter-primes [composites n]
  (loop [primes [] i 2]
    (if (> i n)
      primes
      (if (aget composites i)
        (recur primes (inc i))
        (recur (conj primes i) (inc i))))))

(declare find-repeating-pattern)

(defn sieve [n]
  (let [composites (make-array Boolean/TYPE (inc n))
        limit (int (Math/sqrt n))]
    (loop [candidate 2]
      (if (> candidate n)
        (filter-primes composites n)
        (if (aget composites candidate)
          (recur (inc candidate))
          (do (when (<= candidate limit)
                (mark-multiples composites candidate n))
              ;(prn candidate (find-repeating-pattern (vec composites)))
              (recur (inc candidate))))))))

(defn get-primes-up-to [n]
  (if (> n 1)
    (sieve n)
    []))

(defn is-prime? [primes candidate]
  (if (empty? primes)
    (= 2 candidate)
    ;(not-any? zero? (map #(rem candidate %) primes))
    (loop [primes primes]
      (if (empty? primes)
        true
        (if (zero? (rem candidate (first primes)))
          false
          (recur (rest primes)))))
    ))

(defn get-next-prime [primes]
  (if (empty? primes)
    2
    (loop [n (inc (last primes))]
      (if (is-prime? primes n)
        n
        (recur (inc n))))))

(defn primes
  ([] (primes 2 [2]))
  ([this-prime current-primes]
   (let [next-prime (get-next-prime current-primes)]
     (lazy-seq
       (cons this-prime
             (primes next-prime (conj current-primes next-prime)))
       ))))

(defn is-repeat? [i j bools]
  (let [n (count bools)]
    (loop [i i
           j j]
      (if (>= j n)
        true
        (if (= (nth bools i) (nth bools j))
          (recur (inc i) (inc j))
          false)))))

(defn find-repeating-pattern
  ([bools]
   (find-repeating-pattern
     (if (vector? bools)
       (into-array Boolean/TYPE bools)
       bools)
     0))
  ([bools pos]
   (let [n (count bools)]
     (if (or (< n 2) (> pos 30))
       [0 0]
       (loop [period 1]
         (if (> period (/ n 2))
           (find-repeating-pattern (rest bools) (inc pos))
           (if (is-repeat? 0 period bools)
             [pos period]
             (recur (inc period)))))))
   ))

(defn gcd [a b]
  (if (zero? b)
    a
    (gcd b (mod a b))))

(defn lcm
  ([a b]
   (/ (* a b) (gcd a b)))
  ([ns]
   (reduce lcm 1N ns))
  )