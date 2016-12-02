(ns day-2.core)

(defn get-key
  "Returns the number in the given direction on the provided
  keypad relative from the current key."
  [direction keypad current-key]
  (-> keypad
      (get current-key)
      (get direction nil)))

(defn char->direction
  "Translates a character into an internal representation
  of directions."
  [c]
  (case c
    \U :up
    \R :right
    \D :down
    \L :left))

(defn next-key
  "Resolves the next key on the provided keypad based
  on the current key and the given character."
  [keypad current-key c]
  (-> (char->direction c)
      (get-key keypad current-key)
      (as-> next
            (if (nil? next) current-key next))))

(defn instruction->key
  "This function processes an instruction, i.e.
  a string of characters, and returns the current
  key on the keypad after the instruction has been
  resolved."
  [keypad current-key instruction]
  (let [fn (partial next-key keypad)]
    (reduce fn current-key instruction)))

(defn process-instructions
  [keypad input]
  (-> (clojure.string/split-lines input)
      (as-> instructions
            (reduce
              (fn [result instruction]
                (let [current-key (if (empty? result) 5 (last result))]
                  (conj result (instruction->key keypad current-key instruction))))
              []
              instructions))
      (clojure.string/join)))

(def keypad-pt1
  "Use this keypad to solve the first part of the challenge."
  {1 {:right 2 :down 4}
   2 {:left 1 :right 3 :down 5}
   3 {:left 2 :down 6}
   4 {:up 1 :right 5 :down 7}
   5 {:up 2 :left 4 :right 6 :down 8}
   6 {:up 3 :left 5 :down 9}
   7 {:up 4 :right 8}
   8 {:left 7 :up 5 :right 9}
   9 {:left 8 :up 6}})

(def keypad-pt2
  "Use this keypad to solve the second part of the challenge."
  {1 {:down 3}
   2 {:right 3 :down 6}
   3 {:left 2 :up 1 :down 7 :right 4}
   4 {:left 3 :down 8}
   5 {:right 6}
   6 {:left 5 :up 2 :right 7 :down \A}
   7 {:left 6 :up 3 :right 8 :down \B}
   8 {:left 7 :up 4 :right 9 :down \C}
   9 {:left 8}
   \A {:up 6 :right \B}
   \B {:left \A :up 7 :right \C :down \D}
   \C {:left \B :up 8}
   \D {:up \B}})