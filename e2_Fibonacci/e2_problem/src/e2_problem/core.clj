(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn fibs
  ([a b n fs]
   (if (zero? n)
     fs
     (fibs b (+ a b) (dec n) (conj fs b))))
   ([n] (fibs 0 1 n [])))


(defn sum-even-fibs-upto [n]
  (let [fs (fibs 90)]
    (loop [i 0 fibs []]
      (let [fib-i (nth fs i)]
        (if (> fib-i n)
          (reduce + (filter even? fibs))
          (recur (inc i) (conj fibs fib-i)))))))
