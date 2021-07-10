(ns prime-factors.core
  (:require [clojure.set :as set]
            [clojure.data.int-map :as i]))

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