(ns day-9.core)

(defn parse-marker
  "Parses a marker from the given input. Expects the
  input to start with a \(-character."
  [raw-marker]
  (-> (drop 1 raw-marker)
      (drop-last)
      (clojure.string/join)
      (clojure.string/split #"x")
      (as-> result
            {:chars (read-string (first result))
             :times (read-string (second result))})))

(defn next-marker
  "Extracts the next marker from the given input, returning
  its lenght, the times it should be repeated and the remaining
  sequence of chars when the marker is ommitted from the input."
  [input]
  (reduce
    (fn [result current]
      (if (= \) (char current))
        (reduced (into (parse-marker (conj result char)) {:remaining (drop (inc (count result)) input)}))
        (conj result current)))
    []
    input))

(defn handle-marker
  "Executes the marker, returning the processed sequence and any
  remaining characters that were not affected in this process."
  [marker]
  (let [chars (:chars marker)
        times (:times marker)]
    {:processed (flatten (repeat times (take chars (:remaining marker))))
     :remaining (drop chars (:remaining marker))}))

(defn next-marker-pos
  "Retrieves the first marker position in the input."
  [input]
  (.indexOf input \())

(defn process-next
  "Processes the next marker, returning the processed result and
  the remaining tail of characters."
  [input]
  (let [[prev to-process] (split-at (next-marker-pos input) input)
        result (handle-marker (next-marker to-process))]
    {:processed (concat prev (:processed result))
     :remaining (:remaining result)}))

(defn process-pt1
  "Use this to complete part one."
  [input]
  (loop [result []
         remaining (seq input)]
    (if (= -1 (next-marker-pos remaining))
      (concat result remaining)
      (let [current (process-next remaining)]
        (recur (concat result (:processed current)) (:remaining current))))))