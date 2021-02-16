(ns e1.core-spec
  (:require [speclj.core :refer :all]
            [e1.core :refer :all]))

(defn p2 [n] (+ 2 n))

(describe "Sum of multiples of 3 and 5"
  (it "should be zero if limit less than 3"
    (should= 0 (sum-multiples-3-5 0))
    (should= 0 (sum-multiples-3-5 1))
    (should= 0 (sum-multiples-3-5 2)))

  (it "should be 3 for 3 and 4"
    (should= 3 (sum-multiples-3-5 3))
    (should= 3 (sum-multiples-3-5 4)))

  (it "should be 8 for 5"
    (should= 8 (sum-multiples-3-5 5)))

  (it "should be 14 for 6, 7, and 8"
    (should= 14 (sum-multiples-3-5 6))
    (should= 14 (sum-multiples-3-5 7))
    (should= 14 (sum-multiples-3-5 8)))

  (it "should be 23 for 9"
    (should= 23 (sum-multiples-3-5 9)))

  (it "should be 33 for 10 and 11"
    (should= 33 (sum-multiples-3-5 10))
    (should= 33 (sum-multiples-3-5 11)))

  (it "should be 45 for 12, 13, and 14"
    (should= 45 (sum-multiples-3-5 12))
    (should= 45 (sum-multiples-3-5 13))
    (should= 45 (sum-multiples-3-5 14)))

  (it "should be 60 for 15, 16, and 17"
    (should= 60 (sum-multiples-3-5 15))
    (should= 60 (sum-multiples-3-5 16))
    (should= 60 (sum-multiples-3-5 17))))

(describe "General sum of many multiples"
  (it "Should calculate multiples of 3 and 5 as above"
    (doseq [n (range 50)]
      (should= (sum-multiples-3-5 n)
               (sum-multiples [3 5] n))))

  (it "should be zero if no factors"
    (should= 0 (sum-multiples [] 1000)))

  (it "should be 5050 if summing multiples of 1 to 100"
    (should= 5050 (sum-multiples [1] 100))))

(describe "Fast sum of multiples"
  (it "should work for one multiple"
    (should= (sum-multiples [2] 100)
             (fast-sum-multiples [2] 100)))

  (it "should work for two multiples"
    (should= (sum-multiples [3 5] 100)
             (fast-sum-multiples [3 5] 100))))

(describe "Fast sum of multiples"
  (context "one factor"
    (for [_ (range 100)]
      (let [limit 5000
            factors [(inc (rand-int 20))]]
        (it (pr-str factors limit " should match")
          (should= (sum-multiples factors limit)
                   (fast-sum-multiples factors limit))))))

  (context "two factors"
    (for [_ (range 100)]
      (let [limit 5000
            factors [(inc (rand-int 20))
                     (inc (rand-int 20))]]
        (it (pr-str factors limit " should match")
          (should= (sum-multiples factors limit)
                   (fast-sum-multiples factors limit))))))

  (context "three factors"
    (it "works for 2 3 5"
      (should= (sum-multiples [2 3 5] 30)
               (fast-sum-multiples [2 3 5] 30)))

    (for [_ (range 100)]
      (let [limit 5000
            factors [(inc (rand-int 30))
                     (inc (rand-int 30))
                     (inc (rand-int 30))]
            expected (sum-multiples factors limit)
            actual (fast-sum-multiples factors limit)]
        (it (pr-str factors limit " should match: "
                    (- actual expected))
          (should= expected actual)))))

  (context "four factors"
      (it "works for 2 3 5 7"
        (should= (sum-multiples [2 3 5 7] 210)
                 (fast-sum-multiples [2 3 5 7] 210)))

      (for [_ (range 100)]
        (let [limit 10000
              factors [(p2 (rand-int 30))
                       (p2 (rand-int 30))
                       (p2 (rand-int 30))
                       (inc (rand-int 30))]
              expected (sum-multiples factors limit)
              actual (fast-sum-multiples factors limit)]
          (it (pr-str factors limit " should match: "
                      (- actual expected))
            (should= expected actual))))))

(describe "General Fast sum of multiples"
  (it "works for 2 3 5 7 11"
    (let [factors [2 3 5 7 11]
          limit (reduce * factors)]
      (should= (sum-multiples factors limit)
               (fast-sum-multiples factors limit))))

  (it "works for 2 3 5 7 11"
    (let [factors [2 3 5 7 11 13]
          limit (reduce * factors)]
      (should= (sum-multiples factors limit)
               (fast-sum-multiples factors limit))))

  (for [_ (range 100 )]
    (let [n-factors (+ 2 (rand-int 6))
          factors (repeatedly
                    n-factors
                    #(inc (rand-int 30)))
          limit (rand-int 100000)]
      (it (str "should match " (pr-str factors) " " limit)
        (should= (sum-multiples factors limit)
                 (fast-sum-multiples factors limit))))))
