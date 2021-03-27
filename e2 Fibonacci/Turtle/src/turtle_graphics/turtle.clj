(ns turtle-graphics.turtle
  (:require [quil.core :as q]))

(defn make []
  {:position [0 0]
   :heading 0
   :velocity 0
   :distance 0
   :state :idle})

(def WIDTH 10)
(def HEIGHT 15)

(defn draw [turtle]
  (let [[x y] (:position turtle)
        heading (q/radians (:heading turtle))
        base-left (- (/ WIDTH 2))
        base-right (/ WIDTH 2)
        tip HEIGHT]
    (q/stroke 0)
    (q/with-translation
      [x y]
      (q/with-rotation
        [heading]
        (q/line 0 base-left 0 base-right)
        (q/line 0 base-left tip 0)
        (q/line 0 base-right tip 0)))
    )
  )

(defn heading [turtle heading]
  (assoc turtle :heading heading)
  )

(defn velocity [turtle velocity]
  (assoc turtle :velocity velocity)
  )

(defn update-turtle [{:keys [position velocity heading distance state] :as turtle}]
  (let [step (min velocity distance)
        distance (- distance step)
        state (if (zero? distance) :idle state)
        velocity (if (zero? distance) 0 velocity)
        radians (q/radians heading)
        [x y] position
        vx (* step (Math/cos radians))
        vy (* step (Math/sin radians))
        position [(+ x vx) (+ y vy)]]
    (assoc turtle :position position
                  :distance distance
                  :velocity velocity
                  :state state))
  )

(defn forward [turtle [distance]]
  (assoc turtle :velocity 5 :distance distance :state :busy)
  )

(defn handle-command [turtle [cmd & args :as command]]
  (prn command)
  (condp = cmd
    :forward (forward turtle args)

    :else turtle)
  )
