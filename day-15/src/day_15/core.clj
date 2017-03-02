(ns day-15.core)

(def discs-pt1 [{:num-pos 13 :start-pos 1}
                {:num-pos 19 :start-pos 10}
                {:num-pos 3 :start-pos 2}
                {:num-pos 7 :start-pos 1}
                {:num-pos 5 :start-pos 3}
                {:num-pos 17 :start-pos 5}])

(def discs-pt2 (conj discs-pt1 {:num-pos 11 :start-pos 0}))

(defn disc-pos
  "Calculates the position of the provided disc at the time t"
  [disc t]
  (-> (:start-pos disc)
      (+ t)
      (mod (:num-pos disc))))

(defn discs-are-in-pos?
  "Determines if all discs are in position so that the
  ball can fall through without falling off."
  [discs t]
  (->> (inc t)
       (iterate inc)
       (map vector discs)
       (map #(apply disc-pos %))
       (apply +)
       (zero?)))

(defn t-when-to-drop
  "This function returns the value of t when one
  should drop the ball in order for it to fall
  through all discs. Use this to solve the puzzles."
  [discs]
  (reduce
    (fn [_ t]
      (when (discs-are-in-pos? discs t)
        (reduced t)))
    (range)))