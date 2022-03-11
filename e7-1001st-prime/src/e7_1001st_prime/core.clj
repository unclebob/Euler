(ns e7-1001st-prime.core
  (:import (sieve Sieve)))

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