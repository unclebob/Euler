(ns euler6.core)

(defn square [n] (* n n))

(defn sum [ns] (reduce + ns))

(defn squares [ns] (map square ns))

(defn nat [n] (range 1N (inc n)))

(defn e6 [n]
  (let [nat-n (nat n)
        sum-squares (sum (squares nat-n))
        square-sum (square (sum nat-n))]
    (- square-sum sum-squares)))

(defn sum-n [n] (/ (* n (inc n)) 2))
(defn sum-n2 [n] (/ (* n (inc n) (inc (* 2 n))) 6))

(defn e6-2 [n]
  (- (square (sum-n n)) (sum-n2 n)))
