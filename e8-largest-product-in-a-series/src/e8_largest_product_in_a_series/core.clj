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

(defn find-largest-product [n digits]
  (let [groups (gather-groups n digits)
        products (map #(reduce *' %) groups)
        pairs (apply hash-map (interleave products groups))
        max-product (apply max products)
        group (get pairs max-product)
        ]
    [max-product group]))
