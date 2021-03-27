(ns turtle-graphics.turtle
  (:require [quil.core :as q]))

(defn make []
  {:position [0 0]
   :heading 0})

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

(defn update-turtle [{:keys [position velocity heading] :as turtle}]
  (let [radians (q/radians heading)
        [x y] position
        vx (* velocity (Math/cos radians))
        vy (* velocity (Math/sin radians))
        position [(+ x vx) (+ y vy)]]
    (assoc turtle :position position))
  )

(defn handle-command [turtle [cmd & args :as command]]
  (prn command)
  (condp = cmd
    :forward (assoc turtle :velocity 5)

    :else turtle)
  )
