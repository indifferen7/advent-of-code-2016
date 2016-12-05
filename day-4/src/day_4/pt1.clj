(ns day-4.pt1
  (:require [clojure.string :refer :all]))

(defn id-and-checksum
  "Extracts id and checksum from a string in the
   form of 'id[checksum]'"
  [string]
  (map #(clojure.string/replace % #"\]" "") (split string #"\[")))

(defn enc-name-comparator
  "The comparator used to sort the encrypted
  name map."
  [freq k1 k2]
  (let [val1 (get freq k1)
        val2 (get freq k2)]
    (if (= val1 val2)
      (compare k1 k2)
      (compare val2 val1))))

(defn sort-enc-name-map
  "Sorts the encrypted name map."
  [enc-name-map]
  (into
    (sorted-map-by (partial enc-name-comparator enc-name-map))
    enc-name-map))

(defn extract-checksum
  "Extracts the checksum from a series of letters."
  [letters]
  (->> (frequencies letters)
       (sort-enc-name-map)
       (keys)
       (take 5)
       (join)))

(defn valid-room
  "This function returns a vector consisting of the room
  name and id if valid, otherwise nil."
  [name]
  (let [parts (split name #"-")
        letters (mapcat seq (drop-last parts))
        [id checksum] (id-and-checksum (last parts))]
    (cond
      (= checksum (extract-checksum letters)) [(join "-" (drop-last parts)) (read-string id)]
      :else nil)))

(defn valid-rooms
  "Get all valid room IDs from the input-string."
  [input-string]
  (->> (split-lines input-string)
       (map valid-room)
       (filter (complement nil?))
       (into {})))

(defn valid-rooms-count
  "Use this to finish the first part of day four."
  [input-string]
  (->> (valid-rooms input-string)
       (vals)
       (reduce +)))