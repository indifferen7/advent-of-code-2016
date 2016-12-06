(ns day-6.core)

(defn letter-frequencies
  "Returns the frequencies of characters, ordered by occurrence,
  resulting in a structure such as {\\b 3, \\a 2} and so on."
  [letters]
  (let [frequencies (frequencies letters)]
    (into
      (sorted-map-by
        (fn [key1 key2]
          (compare (get frequencies key2) (get frequencies key1))))
      frequencies)))

(defn freq
  "Use this to complete part one and two of day six. This function
  returns the result of mapping func on each column sorted by
  character occurrence."
  [lines func]
  (let [transposed-lines (apply map list lines)
        freqs (map letter-frequencies transposed-lines)]
    (clojure.string/join (map #(func (keys %)) freqs))))