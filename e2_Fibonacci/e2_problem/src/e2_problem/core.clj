(ns e2-problem.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn fib [n]
  (if (< n 2)
    1
    (+ (fib (- n 2))
       (fib (- n 1)))))
