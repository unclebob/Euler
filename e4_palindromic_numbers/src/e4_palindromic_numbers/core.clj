(ns e4-palindromic-numbers.core
  (:require [clojure.string :as str]))

(defn make-palindrome [n]
  (let [prefix (str n)
        suffix (str/join (reverse prefix))]
    (Integer/parseInt (str/join [prefix suffix]))))

(defn make-palindromes []
  (for [n (reverse (range 100 1000))]
        (make-palindrome n)))

(defn factor-pairs [n]
  (let [limit (int (Math/sqrt n))
        pairs (for [x (range 1 (inc limit))]
                (if (zero? (mod n x))
                  [x (quot n x)]
                  nil))]
    (remove nil? pairs)))

(defn three-digit-pair? [[a b]]
  (and (< 99 a 1000)
       (< 99 b 1000)))

(defn find-greatest-palindrome []
  (loop [palindromes (doall (make-palindromes))]
    (if (empty? palindromes)
      nil
      (let [pairs (doall (factor-pairs (first palindromes)))
            good-pairs (doall (filter three-digit-pair? pairs))]
        (if (empty? good-pairs)
          (recur (rest palindromes))
          [(first palindromes) (first good-pairs)])))
    )
  )

(defn fast-find-greatest-palindrome []
  (loop [seed 999
         palindrome 999999
         factor 999]
    (if (and
          (zero? (rem palindrome factor))
          (> 999 (quot palindrome factor)))
      palindrome
      (if (> 999 (quot palindrome factor))
        (recur seed palindrome (dec factor))
        (recur (dec seed) (make-palindrome (dec seed)) 999)))
    ))