(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn fibs
  ([a b]
   (lazy-seq
     (cons a (fibs b (+ a b)))))
  ([] (fibs 1N 1N))
  )

(defn sum-even-fibs-upto [n]
  (loop [fs (fibs) sum 0]
    (let [fib (first fs)]
      (if (> fib n)
        sum
        (recur (rest fs)
               (if (even? fib)
                 (+ sum fib)
                 sum))))))

(defn ratio [n]
  (let [fs (fibs)
        numerator (nth fs (inc n))
        denominator (nth fs n)]
    (double (/ numerator denominator))))

