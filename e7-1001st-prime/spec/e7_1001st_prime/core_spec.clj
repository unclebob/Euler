(ns e7-1001st-prime.core-spec
  (:require [speclj.core :refer :all]
            [e7-1001st-prime.core :refer :all]))

;(describe "primes using fast java sieve"
;  (it "should find distant prime"
;    (let [primes (fast-primes-up-to 200000N)]
;      (should= 0 (nth primes 10000)))))

;(describe "primes using slow clojure sieve"
;  (it "should find distant prime"
;    (let [primes (get-primes-up-to 3000000000)]
;      (should= 0 (nth primes 100000000)))))

(describe "lazy-primes"
  (context "is-prime?"
    (it "knows the first prime"
      (should-not (is-prime? [] 0))
      (should-not (is-prime? [] 1))
      (should (is-prime? [] 2)))

    (it "knows 3"
      (should (is-prime? [2] 3)))

    (it "knows 5"
      (should-not (is-prime? [2 3] 4))
      (should (is-prime? [2 3] 5))))

  (context "next-prime"
    (it "should find the next primes"
      (should= 2 (get-next-prime []))
      (should= 7 (get-next-prime [2 3 5]))
      (should= 11 (get-next-prime [2 3 5 7]))))

  (context "primes"
    (it "finds primes"
      (let [ps (primes)]
        (should= 2 (first ps))
        (should= 3 (second ps))
        (should= 5 (nth ps 2))
        (should= 7 (nth ps 3)))))
  )

;(describe "primes using lazy primes"
;  (it "should find distant prime"
;    (let [primes (primes)]
;      (should= 0 (nth primes 100000)))))

(describe "find repeats in the sieve"
  (it "finds the degenerate repeat"
    (should= [0 0] (find-repeating-pattern [])))

  (it "finds single and double patterns"
    (should= [0 0] (find-repeating-pattern [true]))
    (should= [0 0] (find-repeating-pattern [true false]))
    (should= [0 0] (find-repeating-pattern [false true]))
    (should= [0 1] (find-repeating-pattern [true true]))
    )

  (it "finds triple patterns"
    (should= [0 1] (find-repeating-pattern [false false false]))
    (should= [0 0] (find-repeating-pattern [false false true]))
    (should= [0 0] (find-repeating-pattern [false true false]))
    (should= [1 1] (find-repeating-pattern [false true true]))
    (should= [1 1] (find-repeating-pattern [true false false]))
    (should= [0 0] (find-repeating-pattern [true false true]))
    (should= [0 0] (find-repeating-pattern [true true false]))
    (should= [0 1] (find-repeating-pattern [true true true])))

  (it "finds quad patterns"
    (should= [0 1] (find-repeating-pattern [false false false false]))
    (should= [0 0] (find-repeating-pattern [false false false true]))
    (should= [0 0] (find-repeating-pattern [false false true false]))
    (should= [2 1] (find-repeating-pattern [false false true true]))
    (should= [2 1] (find-repeating-pattern [false true false false]))
    (should= [0 2] (find-repeating-pattern [false true false true]))
    (should= [0 0] (find-repeating-pattern [false true true false]))
    (should= [1 1] (find-repeating-pattern [false true true true]))
    (should= [1 1] (find-repeating-pattern [true false false false]))
    (should= [0 0] (find-repeating-pattern [true false false true]))
    (should= [0 2] (find-repeating-pattern [true false true false]))
    (should= [2 1] (find-repeating-pattern [true false true true]))
    (should= [2 1] (find-repeating-pattern [true true false false]))
    (should= [0 0] (find-repeating-pattern [true true false true]))
    (should= [0 0] (find-repeating-pattern [true true true false]))
    (should= [0 1] (find-repeating-pattern [true true true true])))

  (it "detects repeats"
    (should-not (is-repeat? 0 1 [true false]))
    (should (is-repeat? 0 1 [true true]))
    (should-not (is-repeat? 0 2 [false true true false]))

    (should (is-repeat? 0 2 [false true false true false]))

    )
  )