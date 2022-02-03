(ns smallest-multiple)

(defn odious? [n]
  (let [b (Integer/toString n 2)
        ones (filter #(= \1 %) (seq b))]
    (odd? (count ones))))

(defn odious [n]
  (filter odious? (range 1 (inc n))))

(defn evil [n]
  (remove odious? (range 1 (inc n))))

(defn tri [n]
  (/ (+ (* n n) n) 2))

(defn tris [n]
  (map tri (range 1 (inc n))))

(defn fibs
  ([a b]
   (lazy-seq
     (cons a (fibs b (+ a b)))))
  ([] (fibs 1N 1N))
  )
e
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

(defn gcd [a b]
  (if (zero? b)
    a
    (gcd b (mod a b))))

(defn lcm
  ([a b]
   (/ (* a b) (gcd a b)))
  ([ns]
   (reduce lcm 1N ns))
  )

(defn fac [n] (apply * (range 1N (inc n))))

(defn ratio [n]
  (let [f (fac n)
        l (lcm (range 1N (inc n)))]
    [f l (/ f l)]))