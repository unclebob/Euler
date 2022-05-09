(ns e8-largest-product-in-a-series.core
  (:require [clojure.string :as string]))

(defn parse-input [text]
  (let [lines (string/split-lines text)
        lines (map #(.trim %) lines)]
    (apply str lines)))

(defn string->digits [s]
  (map #(- (int %) (int \0)) s))

(defn gather-groups [group-size items]
  (loop [items items
         groups []]
    (if (> group-size (count items))
      groups
      (recur (rest items) (conj groups (take group-size items))))))

(def logs (map #(Math/log %) (range 10)))

(defn log-of-group [group]
  (map #(nth logs %) group))

(defn find-largest-product [n digits]
  (let [groups (gather-groups n digits)
        groups (filter #(not-any? zero? %) groups)]
    (if (empty? groups)
      [0 []]
      (let [sums (map #(reduce + (log-of-group %)) groups)
            pairs (apply hash-map (interleave sums groups))
            max-sum (apply max sums)
            group (get pairs max-sum)]
        [(reduce *' group) group]))))

(defn find-longest-product [digits]
  (loop [n 1
         result []]
    (let [[product group] (find-largest-product n digits)]
      (if (zero? product)
        result
        (recur (inc n) [product group])))
    )
  )
