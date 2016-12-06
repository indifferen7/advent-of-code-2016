(ns day-5.core)

(defn input->md5hex
  "A hexadecimal representation of the input string,
  after undergoing an md5 hash treatment."
  [input]
  (let [input-bytes (.getBytes input "UTF-8")
        algorithm (java.security.MessageDigest/getInstance "MD5")
        digested (.digest algorithm input-bytes)]
    (format "%032x" (new java.math.BigInteger 1 digested))))

(defn valid-hash?
  "Determines if the hash is valid according to the
  rules of day five."
  [hash]
  (clojure.string/starts-with? hash "00000"))

(defn hack-da-password-pt1
  "Use this to solve part one of day five."
  [puzzle-input]
  (loop [result [] i 0]
    (if (= 8 (count result))
      (clojure.string/join result)
      (let [hash (input->md5hex (str puzzle-input i))]
        (if (valid-hash? hash)
          (recur (conj result (nth hash 5)) (inc i))
          (recur result (inc i)))))))

(defn okay-to-insert?
  "Tells whether or it is okay insert something
  in the map at the key. The rules of the challenge
  states that we cannot have more than 8 keys, ranging
  from 0-7."
  [key map]
  (and
    (<= 0 (Character/digit (char key) 10) 7)
    (not (contains? map key))))

(defn hack-da-password-pt2
  "Use this to solve part two of day five."
  [puzzle-input]
  (loop [result (sorted-map) i 0]
    (if (= 8 (count result))
      (clojure.string/join (vals result))
      (let [hash (input->md5hex (str puzzle-input i))
            index (nth hash 5)
            letter (nth hash 6)]
        (if (and (valid-hash? hash) (okay-to-insert? index result))
          (recur (conj result {index letter}) (inc i))
          (recur result (inc i)))))))