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

;(defn find-largest-product [n digits]
;  (let [groups (gather-groups n digits)
;        groups (filter #(not-any? zero? %) groups)]
;    (if (empty? groups)
;      [0 []]
;      (let [products (map #(reduce *' %) groups)
;            pairs (apply hash-map (interleave products groups))
;            max-product (apply max products)
;            group (get pairs max-product)]
;        [max-product group]))))

(defn find-largest-product [n digits]
  (loop [digits digits
         p (reduce *' (take n digits))
         pmax 0
         group (take n digits)]
    (let [
          pmax (max pmax p)
          group (if (= p pmax) (take n digits) group)
          first-digit (first digits)
          rest-digits (doall (rest digits))
          next-n (doall (take n rest-digits))]
      (cond
        (>= n (count digits))
        [pmax group]

        (zero? first-digit)
        (recur rest-digits
               (reduce *' next-n)
               pmax
               group)

        :else
        (recur rest-digits
               (/ (*' p (last next-n)) first-digit)
               pmax
               group)))))

(defn find-longest-product [digits]
  (loop [n 1
         result []]
    (let [[product group] (find-largest-product n digits)]
      (if (zero? product)
        result
        (recur (inc n) [product group])))
    )
  )
