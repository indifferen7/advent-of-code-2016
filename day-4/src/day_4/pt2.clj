(ns day-4.pt2)

(def letters (seq "abcdefghijklmnopqrstuvwxyz"))

(defn start-with
  [letter]
  (->> letters
       (split-with (complement #{letter}))
       (reverse)
       (flatten)))

(defn next-char
  [n c]
  (if (= c \-)
    \space
    (nth (cycle (start-with c)) n)))

(defn decrypt-name
  [name id]
  (->> (map (partial next-char id) name)
       (apply str)))

(defn real-name-search->id
  "Use this to solve part two. The function takes the valid-rooms
  result from part one and a string that will be matched. The first
  match is returned, otherwise nil."
  [id-by-rooms q]
  (reduce-kv
    (fn [_ name id]
      (cond
        (.contains (decrypt-name name id) q) (reduced id)
        :else nil))
    nil
    id-by-rooms))