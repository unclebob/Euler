(ns prime-factors.core
  (:import (sieve Sieve)))

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

(defn mark-multiples [composites prime n]
  (loop [i (* 2 prime)]
    (when (<= i n)
      (aset composites i true)
      (recur (+ i prime))))
  )

(defn filter-primes [composites n]
  (loop [primes [] i 2]
    (if (> i n)
      primes
      (if (aget composites i)
        (recur primes (inc i))
        (recur (conj primes i) (inc i))))))

(defn sieve [n]
  (let [composites (make-array Boolean/TYPE (inc n))
        limit (int (Math/sqrt n))]
    (loop [candidate 2]
      (if (> candidate n)
        (filter-primes composites n)
        (if (aget composites candidate)
          (recur (inc candidate))
          (do (when (<= candidate limit)
                (mark-multiples composites candidate n))
              (recur (inc candidate))))))))

(defn get-primes-up-to [n]
  (if (> n 1)
    (sieve n)
    []))

(defn fast-primes-up-to [n]
  (vec (Sieve/primesUpTo n)))

(defn find-twins [ns]
  (loop [twins [] ns ns]
    (if (< (count ns) 2)
      twins
      (let [diff (- (second ns) (first ns))]
        (if (= 2 diff)
          (recur (conj twins (first ns)) (rest ns))
          (recur twins (rest ns)))))))

(defn print-gap-frequency-graph [gap-frequencies]
  (let [gaps (keys gap-frequencies)
        max-gap (apply max gaps)
        log-freqs (into {} (map #(vector (first %) (Math/log (second %))) gap-frequencies))
        max-log-freq (apply max (vals log-freqs))
        magnification (/ 90 max-log-freq)
        gap-range (range 2 max-gap 2)]
    (doseq [gap gap-range]
      (printf "%5d: %8d %s\n"
              gap
              (get gap-frequencies gap 0)
              (apply str (repeat (* magnification (get log-freqs gap 0)) "*"))))))

(defn get-last-digit-frequencies [ns modulus]
  (let [last-digits (map #(mod % modulus) ns)
        last-digit-frequencies (frequencies last-digits)]
    last-digit-frequencies))

(defn print-last-digit-frequencies [ns modulus]
  (let [last-digits (get-last-digit-frequencies ns modulus)
        freqs (remove #(< % 2) (vals last-digits))
        min-freq (apply min freqs)
        max-freq (apply max freqs)
        mean-freq (/ (reduce + freqs) (double (count freqs)))]
    (printf "min: %d, max: %d, mean: %.2f\n" min-freq max-freq mean-freq)
    (prn last-digits)))

(defn get-consecutive-last-digit-frequencies [ns]
  (let [first (map #(mod % 10) ns)
        second (rest first)
        consecutives (map #(str %1 "-" %2) first second)
        consecutive-frequencies (frequencies consecutives)]
    consecutive-frequencies)
  )


(defn twin-density [n]
  (let [primes (fast-primes-up-to n)
        gaps (map #(- %1 %2) (rest primes) primes)
        mean-gap (/ (reduce + gaps) (double (count gaps)))
        gap-frequencies (frequencies gaps)
        nprimes (count primes)
        twins (find-twins primes)
        twin-gaps (map #(- %1 %2) (rest twins) twins)
        longest-twin-gap (apply max twin-gaps)
        ntwins (count twins)
        prime-density (double (/ nprimes n))
        twin-density (double (/ ntwins n))
        twin-prevalence (double (/ ntwins nprimes))]
    (printf "primes: %d, twins %d\n" nprimes ntwins)
    (printf "prime-density:    %.4f\n" prime-density)
    (printf "twin-density:     %.4f\n" twin-density)
    (printf "twin-prevalence:  %.4f\n" twin-prevalence)
    (printf "longest twin gap: %d\n" longest-twin-gap)
    (printf "longest gap:      %d\n" (apply max gaps))
    (printf "mean gap:         %.4f\n" mean-gap)
    (print-gap-frequency-graph gap-frequencies)
    (printf "\nLast digit frequencies:")
    ))