(ns prime-factors.core-spec
  (:require [speclj.core :refer :all]
            [prime-factors.core :refer :all]))

(describe
  "factor primes"
  (it "factors 1 -> []"
      (should= [] (factors-of 1)))
  (it "factors 2 -> [2]"
      (should= [2] (factors-of 2)))
  (it "factors 3 -> [3]"
      (should= [3] (factors-of 3)))

  )
