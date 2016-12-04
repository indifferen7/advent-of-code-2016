(ns day-3.core)

(defn valid-triangle
  "Determines if a triangle is valid"
  [[a b c]]
  (and
    (> (+ a b) c)
    (> (+ b c) a)
    (> (+ a c) b)
    ))

(defn valid-triangles
  "Returns all valid triangles"
  [sides]
  (filter valid-triangle sides))

(defn parse-input-pt1
  "Completes the first part of day 3"
  [input-string]
  (->> input-string
    (clojure.string/split-lines)
       (map clojure.string/trim)
       (map #(clojure.string/split % #"\s+"))
       (map #(map read-string %))))

(defn parse-input-pt2
  "Completes the second part of day 3"
  [input-string]
  (->> input-string
       (parse-input-pt1)
       (apply map list)
       (flatten)
       (partition 3)))