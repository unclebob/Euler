(ns turtle-graphics.turtle
  (:require [quil.core :as q]
            [clojure.spec.alpha :as s]))

(s/check-asserts true)
(s/def ::position (s/tuple number? number?))
(s/def ::heading (s/and number? #(<= 0 % 360)))
(s/def ::velocity number?)
(s/def ::distance number?)
(s/def ::omega number?)
(s/def ::angle number?)
(s/def ::state #{:idle :busy})
(s/def ::pen #{:up :down})
(s/def ::pen-start (s/or :nil nil?
                         :pos (s/tuple number? number?)))
(s/def ::line-start (s/tuple number? number?))
(s/def ::line-end (s/tuple number? number?))
(s/def ::line (s/keys :req-un [::line-start ::line-end]))
(s/def ::lines (s/coll-of ::line))
(s/def ::turtle (s/keys :req-un [::position
                                 ::heading
                                 ::velocity
                                 ::distance
                                 ::omega
                                 ::angle
                                 ::pen
                                 ::lines
                                 ::state]
                        :opt-un [::pen-start]))

(defn make []
  {:post [(s/assert ::turtle %)]}
  {:position [0.0 0.0]
   :heading 0.0
   :velocity 0.0
   :distance 0.0
   :omega 0.0
   :angle 0.0
   :pen :up
   :lines []
   :state :idle})

(def WIDTH 10)
(def HEIGHT 15)

(defn draw [turtle]
  (when (= :down (:pen turtle))
    (q/stroke 0)
    (q/stroke-weight 1)
    (q/line (:pen-start turtle) (:position turtle)))

  (doseq [line (:lines turtle)]
    (q/line (:line-start line) (:line-end line)))

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
  (assoc turtle :heading heading))

(defn velocity [turtle velocity]
  (assoc turtle :velocity velocity))

(defn position [turtle position]
  (assoc turtle :position position))

(defn distance [turtle distance]
  (assoc turtle :distance distance))

(defn omega [turtle omega]
  (assoc turtle :omega omega))

(defn angle [turtle angle]
  (assoc turtle :angle angle))

(defn state [turtle state]
  (assoc turtle :state state))

(defn pen-down [{:keys [pen position pen-start] :as turtle}]
  (assoc turtle :pen :down
                :pen-start (if (= :up pen) position pen-start)))

(defn pen-up [{:keys [pen pen-start position lines] :as turtle}]
  (if (= :up pen)
    turtle
    (let [new-line {:line-start pen-start
                    :line-end position}
          lines (conj lines new-line)]
      (assoc turtle :pen :up
                    :pen-start nil
                    :lines lines))))

(defn update-position [{:keys [position velocity heading distance] :as turtle}]
  (let [step (min (q/abs velocity) distance)
        distance (- distance step)
        step (if (neg? velocity) (- step) step)
        radians (q/radians heading)
        [x y] position
        vx (* step (Math/cos radians))
        vy (* step (Math/sin radians))
        position [(+ x vx) (+ y vy)]
        ]
    (assoc turtle :position position
                  :distance distance
                  :velocity (if (zero? distance) 0.0 velocity))))

(defn update-heading [{:keys [heading omega angle] :as turtle}]
  (let [angle-step (min (q/abs omega) angle)
        angle (- angle angle-step)
        angle-step (if (neg? omega) (- angle-step) angle-step)
        heading (mod (+ heading angle-step) 360)]
    (assoc turtle :heading heading
                  :angle angle
                  :omega (if (zero? angle) 0.0 omega))))

(defn update-turtle [turtle]
  {:post [(s/assert ::turtle %)]}
  (if (= :idle (:state turtle))
    turtle
    (let [{:keys [distance state angle lines position pen pen-start] :as turtle}
          (-> turtle
              (update-position)
              (update-heading))
          done? (and (zero? distance)
                     (zero? angle))
          state (if done? :idle state)
          lines (if (and done? (= pen :down))
                  (conj lines {:line-start pen-start
                               :line-end position})
                  lines)
          pen-start (if (and done? (= pen :down))
                      position
                      pen-start)]
      (assoc turtle :state state :lines lines :pen-start pen-start)))
  )

(defn forward [turtle [distance]]
  (assoc turtle :velocity 5 :distance distance :state :busy))

(defn right [turtle [angle]]
  (assoc turtle :omega 10 :angle angle :state :busy))

(defn handle-command [turtle [cmd & args :as command]]
  (prn command)
  (condp = cmd
    :forward (forward turtle args)
    :right (right turtle args)
    :pen-down (pen-down turtle)

    :else turtle)
  )



