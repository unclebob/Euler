(ns turtle-graphics.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [turtle-graphics.turtle :as turtle]
            [clojure.core.async :as async]))

(def channel (async/chan))

(defn fibs
  ([a b]
   (lazy-seq
     (cons a (fibs b (+ a b)))))
  ([] (fibs 1 1))
  )

(defn forward [distance] (async/>!! channel [:forward distance]))
(defn back [distance] (async/>!! channel [:back distance]))
(defn right [angle] (async/>!! channel [:right angle]))
(defn left [angle] (async/>!! channel [:left angle]))
(defn pen-up [] (async/>!! channel [:pen-up]))
(defn pen-down [] (async/>!! channel [:pen-down]))
(defn hide [] (async/>!! channel [:hide]))
(defn show [] (async/>!! channel [:show]))
(defn weight [weight] (async/>!! channel [:weight weight]))
(defn speed [speed] (async/>!! channel [:speed speed]))

(defn turtle-script [channel]
  (pen-down)
  (speed 50)
  (weight 3)
  (doseq [f (take 20 (fibs))]
    (weight (inc (Math/log (double f))))
    (forward f)
    (right 90))
  )


(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (let [state {:turtle (turtle/make)
               :channel channel}]
    (async/go
      (turtle-script channel)
      (prn "Turtle script complete"))
    state)
  )

(defn update-state [{:keys [channel] :as state}]
  (let [turtle (:turtle state)
        turtle (turtle/update-turtle turtle)
        command (if (= :idle (:state turtle)) (async/poll! channel) nil)
        turtle (if (some? command) (turtle/handle-command turtle command)
                                   turtle)]
    (assoc state :turtle turtle))
  )

(defn draw-state [state]
  (q/background 240)
  (q/with-translation
    [500 500]
    (let [{:keys [turtle]} state]
      (turtle/draw turtle)))
  )

(defn ^:export -main [& args]
  (q/defsketch turtle-graphics
               :title "Turtle Graphics"
               :size [1000 1000]
               :setup setup
               :update update-state
               :draw draw-state
               :features [:keep-on-top]
               :middleware [m/fun-mode])
  args)
