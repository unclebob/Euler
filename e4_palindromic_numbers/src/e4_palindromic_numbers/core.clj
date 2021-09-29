(ns e4-palindromic-numbers.core
  (:require [clojure.string :as str]))

(defn make-palindrome [n]
  (let [prefix (str n)
        suffix (str/join (reverse prefix))]
    (Integer/parseInt (str/join [prefix suffix]))))

(defn make-palindromes []
  (for [n (reverse (range 1000 10000))]
        (make-palindrome n)))

(defn factor-pairs [n]
  (let [limit (int (Math/sqrt n))
        pairs (for [x (range 1 (inc limit))]
                (if (zero? (mod n x))
                  [x (quot n x)]
                  nil))]
    (remove nil? pairs)))

(defn four-digit-pair? [[a b]]
  (and (< 999 a 10000)
       (< 999 b 10000)))

(defn find-greatest-palindrome []
  (loop [palindromes (doall (make-palindromes))]
    (if (empty? palindromes)
      nil
      (let [pairs (doall (factor-pairs (first palindromes)))
            good-pairs (doall (filter four-digit-pair? pairs))]
        (if (empty? good-pairs)
          (recur (rest palindromes))
          [(first palindromes) (first good-pairs)])))
    )
  )

(defn fast-find-greatest-palindrome []
  (loop [seed 9999
         palindrome 99999999
         factor 9999]
    (if (and
          (zero? (rem palindrome factor))
          (> 9999 (quot palindrome factor)))
      palindrome
      (if (> 9999 (quot palindrome factor))
        (recur seed palindrome (dec factor))
        (recur (dec seed) (make-palindrome (dec seed)) 9999)))
    ))