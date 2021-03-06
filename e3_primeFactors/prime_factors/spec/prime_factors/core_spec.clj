(ns prime-factors.core-spec
  (:require [speclj.core :refer :all]
            [prime-factors.core :refer :all]))

(defn power2 [n]
  (apply * (repeat n 2N)))

(describe "factor primes"
  (it "factors 1 -> []"
    (should= [] (factors-of 1)))
  (it "factors 2 -> [2]"
    (should= [2] (factors-of 2)))
  (it "factors 3 -> [3]"
    (should= [3] (factors-of 3)))
  (it "factors 4 -> [2 2]"
    (should= [2 2] (factors-of 4)))
  (it "factors 5 -> [5]"
    (should= [5] (factors-of 5)))
  (it "factors 6 -> [2 3]"
    (should= [2 3] (factors-of 6)))
  (it "factors 7 -> [7]"
    (should= [7] (factors-of 7)))
  (it "factors 8 -> [2 2 2]"
    (should= [2 2 2] (factors-of 8)))
  (it "factors 9 -> [3 3]"
    (should= [3 3] (factors-of 9)))
  (it "factors lots"
    (should= [2 2 3 3 5 7 11 11 13]
             (factors-of (* 2 2 3 3 5 7 11 11 13))))
  (it "factors Euler 3"
    (should= [71 839 1471 6857] (factors-of 600851475143)))

  (it "factors mersenne 2^31-1"
    (should= [2147483647] (factors-of (dec (power2 31)))))

  ;(it "factors mersenne 2^61-1"
  ;  (let [m61 (dec (power2 61))]
  ;    (should= [m61] (factors-of m61))))
  ;
  ;(it "factors m31*m31"
  ;  (let [m31 (dec (power2 31))]
  ;    (should= [m31 m31] (factors-of (* m31 m31)))))
  )

(describe "get primes up to"
  (it "1 -> []]"
    (should= [] (get-primes-up-to 1)))
  (it "2 -> [2]"
    (should= [2] (get-primes-up-to 2)))
  (it "3 -> [2 3]"
    (should= [2 3] (get-primes-up-to 3)))
  (it "4 -> [2 3]"
    (should= [2 3] (get-primes-up-to 4)))
  (it "5 -> [2 3 5]"
    (should= [2 3 5] (get-primes-up-to 5)))
  (it "30 -> [2 3 5 7 11 13 17 19 23 29]"
    (should= [2 3 5 7 11 13 17 19 23 29] (get-primes-up-to 30)))
  (it "168 primes below 1000"
      (should= 168 (count (get-primes-up-to 1000))))
  (it "78498 primes below 1000000"
        (should= 78498 (count (fast-primes-up-to 1000000))))
  )

(describe "find twin primes"
  (it "[] -> []"
    (should= [] (find-twins [])))
  (it "[1] -> []"
    (should= [] (find-twins [1])))
  (it "[1 1] -> []"
    (should= [] (find-twins [1 1])))
  (it "[1 2] -> []"
      (should= [] (find-twins [1 2])))
  (it "[1 3] -> [1]"
      (should= [1] (find-twins [1 3])))
  (it "[1 3 4 7 9] -> [1 7]"
        (should= [1 7] (find-twins [1 3 4 7 9])))
  (it "should find twin primes up to 100"
    (should= [3 5 11 17 29 41 59 71] (find-twins (fast-primes-up-to 100))))
  (it "should find twin primes up to 1000"
      (should-not (empty? (find-twins (fast-primes-up-to 1000)))))
  (it "should find twin primes up to 10000"
        (should-not (empty? (find-twins (fast-primes-up-to 10000)))))
  (it "should find twin primes up to 100000"
        (should-not (empty? (find-twins (fast-primes-up-to 100000)))))
  (it "should find twin primes up to 1,000,000"
        (should-not (empty? (find-twins (fast-primes-up-to 1000000)))))
  (it "should find twin primes up to 10,000,000"
          (should-not (empty? (find-twins (fast-primes-up-to 10000000)))))
  )
