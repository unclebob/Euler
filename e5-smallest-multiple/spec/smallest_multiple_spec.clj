(ns smallest-multiple-spec
  (:require [speclj.core :refer :all]
            [smallest-multiple :refer :all])
  )


(describe "smallest-multiple"
  (it "is 1 with no inputs"
    (should= 1 (lcm [])))

  (it "is 1 with [1]"
    (should= 1 (lcm [1])))

  (it "is 2 with [2]"
    (should= 2 (lcm [2])))

  (it "is 2 with [1 2]"
    (should= 2 (lcm [1 2])))

  (it "is 4 with [2 4]"
    (should= 4 (lcm [2 4])))

  (it "is 2520 with (range 1 11)"
    (should= 2520 (lcm (range 1 11))))

  (it "is answer with (range 1 21)"
    (should= 232792560 (lcm (range 1 21))))
  )

(describe "gcd"
  (it "is 1 if mutually prime"
    (should= 1 (gcd 3 5)))

  (it "is n if a = b"
    (should= 3 (gcd 3 3)))

  (it "is b if b divides a"
    (should= 3 (gcd 6 3)))

  (it "is the largest n such that n divides a and b"
    (should= 4 (gcd 8 12)))

  )















;
;  (it "is 12 with [2 3 4]"
;    (should= 12 (lcm [2 3 4])))
;

;

;  )


;

;

;

;
;  )