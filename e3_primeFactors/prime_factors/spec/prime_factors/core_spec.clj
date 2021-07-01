(ns prime-factors.core-spec
  (:require [speclj.core :refer :all]
            [prime-factors.core :refer :all]))

(describe "factor primes"
  (it "factors 1"
    (should= [] (factors-of 1))))
