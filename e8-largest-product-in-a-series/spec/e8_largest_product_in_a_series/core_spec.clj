(ns e8-largest-product-in-a-series.core-spec
  (:require [speclj.core :refer :all]
            [e8-largest-product-in-a-series.core :refer :all]))

(describe "parsing input"
  (it "gathers lines into a string"
    (should= "1234" (parse-input "1 \n2 \n3 \n4"))
    (should= "12345678" (parse-input "12\n345\n6\n78 ")))

  (it "can read the actual input data"
    (let [data (slurp "data.txt")
          data (parse-input data)]
      (should= 1000 (count data)))))

(describe "prepare data"
  (it "turns string into digits"
    (should= [] (string->digits ""))
    (should= [1] (string->digits "1"))
    (should= [0 1 2 3 4 5 6 7 8 9] (string->digits "0123456789"))))

(describe "sliding window"
  (it "walks list selecting n adjacent items"
    (should= [] (gather-groups 1 []))
    (should= [[1]] (gather-groups 1 [1]))
    (should= [] (gather-groups 2 [1]))
    (should= [[1 2]] (gather-groups 2 [1 2]))
    (should= [[1] [2] [3]] (gather-groups 1 [1 2 3]))
    (should= [[1 2] [2 3]] (gather-groups 2 [1 2 3]))))


(def data (-> "data.txt" slurp parse-input string->digits))

(describe "find largest product"
  (it "finds largest product of 4"
    (should= [5832 [9 9 8 9]] (find-largest-product 4 data)))

  (it "finds largest product of 13"
    (should= [23514624000 [5 5 7 6 6 8 9 6 6 4 8 9 5]] (find-largest-product 13 data)))

  (it "finds largest product of 30"
    (should= 374476218826752000000N (first (find-largest-product 30 data))))

  (it "finds largest product of 100"
    (should= 0 (first (find-largest-product 100 data))))
  )

(describe "finding longest strings"
  (it "finds longest non-zero product"
    (let [[product group] (find-longest-product data)]
      (should= 2412446685431734624320887406251212800000000N product)
      (should= 69 (count group))

      )
    )
  )


