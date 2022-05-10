(ns e8-largest-product-in-a-series.core
  (:require [clojure.string :as string]))

(defn factors-of [n]
  (loop [factors [] n n divisor 2]
    (if (> n 1)
      (cond
        (> divisor (Math/sqrt n))
        (conj factors n)
        (= 0 (mod n divisor))
        (recur (conj factors divisor)
               (quot n divisor)
               divisor)
        :else
        (recur factors n (inc divisor)))
      factors)))

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

(defn find-largest-product [n digits]
  (let [groups (gather-groups n digits)
        groups (filter #(not-any? zero? %) groups)]
    (if (empty? groups)
      [0 []]
      (let [products (map #(reduce *' %) groups)
            pairs (apply hash-map (interleave products groups))
            max-product (apply max products)
            group (get pairs max-product)]
        [max-product group]))))

(defn find-longest-product [digits]
  (loop [n 1
         result []]
    (let [[product group] (find-largest-product n digits)]
      (if (zero? product)
        result
        (recur (inc n) [product group])))
    )
  )
