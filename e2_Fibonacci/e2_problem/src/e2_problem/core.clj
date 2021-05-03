(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn fibs
  ([a b]
   (lazy-seq
     (cons a (fibs b (+ a b)))))
  ([] (fibs 1 1))
  )


(defn sum-even-fibs-upto [n]
  (let [fs (fibs)]
    (loop [i 0 fibs []]
      (let [fib-i (nth fs i)]
        (if (> fib-i n)
          (reduce + (filter even? fibs))
          (recur (inc i) (conj fibs fib-i)))))))
