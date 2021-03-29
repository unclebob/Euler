(ns turtle-graphics.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [turtle-graphics.turtle :as turtle]
            [clojure.core.async :as async]))


(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (let [channel (async/chan)
        state {:turtle (-> (turtle/make) (turtle/heading 0) (turtle/velocity 0))
               :channel channel
               :tracks []}]
    (async/go
      (doseq [x (range 2)]
        (async/>! channel [:right 360]))
      (async/>! channel [:pen-down])
      (doseq [x (range 4)]
        (async/>! channel [:forward 100])
        (async/>! channel [:right 90]))
      (prn "done sending commands."))
    state))

(defn update-state [{:keys [turtle channel] :as state}]
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
