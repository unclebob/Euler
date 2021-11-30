(ns smallest-multiple-spec
  (:require [speclj.core :refer :all]
            [smallest-multiple :refer :all])
  )

(describe "smallest-multiple"
  (it "is 1 with no inputs"
    (should= 1 (smallest-multiple [])))

  (it "is 1 with [1]"
    (should= 1 (smallest-multiple [1])))

  (it "is 2 with [2]"
    (should= 2 (smallest-multiple [2])))

  (it "is 2 with [1 2]"
    (should= 2 (smallest-multiple [1 2])))

  (it "is 4 with [2 4]"
    (should= 4 (smallest-multiple [2 4])))

  (it "is 12 with [2 3 4]"
    (should= 12 (smallest-multiple [2 3 4])))

  (it "is 2520 with (range 1 11)"
    (should= 2520 (smallest-multiple (range 1 11))))

  (it "is answer with (range 1 21)"
    (should= 232792560 (smallest-multiple (range 1 21))))
  )