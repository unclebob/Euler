(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn fib
  ([n]
   (if (< n 2)
     1
     (fib (dec n) 1 1)))
  ([n a b]
   (if (zero? n)
     b
     (recur (dec n) b (+ a b)))))

(defn sum-even-fibs-upto [n]
  (loop [i 0 fibs []]
    (let [fib-i (fib i)]
      (if (> fib-i n)
        (reduce + (filter even? fibs))
        (recur (inc i) (conj fibs fib-i))))))
