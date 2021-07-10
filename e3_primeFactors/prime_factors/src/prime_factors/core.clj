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

(defn remove-factors-of-first [candidates]
  (let [first-candidate (first candidates)
        factors (i/int-set (range first-candidate
                            (inc (last candidates))
                            first-candidate))
        new-candidates (set/difference candidates factors)]
    new-candidates))

(defn sieve [n]
  (loop [candidates (i/int-set (range 2 (inc n)))
         primes []]
    (if (empty? candidates)
      primes
      (recur (remove-factors-of-first candidates)
             (conj primes (first candidates))))
    ))

(defn get-primes-up-to [n]
  (if (> n 1)
    (sieve n)
    []))