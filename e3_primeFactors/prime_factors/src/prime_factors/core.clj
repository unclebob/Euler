(ns prime-factors.core
  (:require [clojure.set :as set]
            [clojure.data.int-map :as i])
  (:import (sieve SieveTest)))

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

(defn fast-primes-up-to [n]
  (seq (sieve.Sieve/primesUpTo n)))

(defn find-twins [ns]
  (loop [twins [] ns ns]
    (if (< (count ns) 2)
      twins
      (let [diff (- (second ns) (first ns))]
        (if (= 2 diff)
          (recur (conj twins (first ns)) (rest ns))
          (recur twins (rest ns)))))))