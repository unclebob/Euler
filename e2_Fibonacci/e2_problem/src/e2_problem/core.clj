(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(def fib
  (memoize
    (fn [n]
      (if (< n 2)
        1
        (+ (fib (- n 2))
           (fib (- n 1)))))))

(defn sum-even-fibs-upto [n]
  (loop [i 0 fibs []]
    (let [fib-i (fib i)]
      (if (> fib-i n)
        (reduce + (filter even? fibs))
        (recur (inc i) (conj fibs fib-i))))))
