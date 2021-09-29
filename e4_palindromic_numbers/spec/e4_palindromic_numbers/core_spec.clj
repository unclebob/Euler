(ns e4-palindromic-numbers.core-spec
  (:require [speclj.core :refer :all]
            [e4-palindromic-numbers.core :refer :all]))

(describe "Palindromic Numbers"
  (it "Will build palindromes from numbers"
    (should= 123321 (make-palindrome 123)))

  (it "Will make all 6 digit palindromes"
    (let [palindromes (make-palindromes)]
      (should= 900 (count palindromes))
      (should= 999999 (first palindromes))
      (should= 100001 (last palindromes))))
  )

(describe "Factors of Palindromic numbers"
  (it "can make factor pairs"
    (should= [[1 19]] (factor-pairs 19))
    (should= [[1 20] [2 10] [4 5]] (factor-pairs 20)))

  (it "can identify three digit pairs"
    (should (four-digit-pair? [100 999]))
    (should-not (four-digit-pair? [99 200]))
    (should-not (four-digit-pair? [200 1000])))
  )

(describe "Greatest Palindromic number product of two three digit numbers"
  (it "will find that number"
    (should= [906609 [913 993]] (find-greatest-palindrome)))
  )
