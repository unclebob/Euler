(ns e1.core
  (:require [clojure.math.combinatorics :as comb]
            [clojure.set :as s]))

(defn sum-multiples-3-5 [limit]
  (let [threes (range 3 (inc limit) 3)
        fives (range 5 (inc limit) 5)]
    (reduce + (set (concat threes fives)))))


(defn sum-multiples [factors limit]
  (let [multiples (set (mapcat
                         #(range % (inc limit) %)
                         factors))]
    (reduce + multiples))
  )

(defn sum-up-to [n]
  (/ (+ n (* n n)) 2))

(defn- floor [n] (int (Math/floor n)))

(defn- sum-multiple-up-to [m n]
  (* m (sum-up-to (floor (/ n m)))))

(defn- get-multiple [[a b]]
  (let [f (min a b)
        m (max a b)]
    (if (zero? (rem m f))
      m
      nil)))

(defn remove-multiples [factors]
  (let [factors (set factors)
        pairs (comb/combinations factors 2)
        multiples (set (keep get-multiple pairs))
        factors (s/difference factors multiples)]
    (seq factors)))

(defn gcd
  [a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm
  [a b]
  (/ (* a b) (gcd a b)))

(defn lcmv [& v] (reduce lcm v))

(defn sum-of-subset-lcms-up-to [f subsets limit]
  (let [tuples (filter #(f (count %)) subsets)
        lcms (map #(apply lcmv %) tuples)
        sums (map #(sum-multiple-up-to % limit) lcms)]
    (reduce + sums)))

(defn fast-sum-multiples [factors limit]
  (let [facs (remove-multiples factors)
        subsets (filter #(pos? (count %)) (comb/subsets facs))]
    (- (sum-of-subset-lcms-up-to odd? subsets limit)
       (sum-of-subset-lcms-up-to even? subsets limit)
        )
    )
  )
