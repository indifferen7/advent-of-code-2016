(ns day-1.core)

(defn- next-direction
  "Returns the next direction if we move either left
  or right when facing the current direction."
  [current-direction left-or-right]
  (case current-direction
    :north (if (= left-or-right :right) :east :west)
    :east (if (= left-or-right :right) :south :north)
    :south (if (= left-or-right :right) :west :east)
    :west (if (= left-or-right :right) :north :south)))

(defn- next-position
  "Determines the next coordinate when travelling a given
  distance towards a given direction from a given coordinate."
  [{:keys [from direction distance]}]
  (let [[x y] from]
    (case direction
      :north [x (+ y distance)]
      :east [(+ x distance) y]
      :south [x (- y distance)]
      :west [(- x distance) y])))

(defn get-visited
  "A not very pretty way to find visited coordinates from one
  point to another. For this function to work, the movement must
  be horizontal or vertical (which is okay to solve the puzzle)."
  [[x1 y1] [x2 y2]]
  (let [range (if (= x1 x2)
                (for [y (range (min y1 y2) (inc (max y1 y2)))] [x1 y])
                (for [x (range (min x1 x2) (inc (max x1 x2)))] [x y1]))]
    (if (or (> x1 x2) (> y1 y2))
      (rest (reverse range))
      (rest range))))

(defn- process-instruction
  "This helper funciton takes the accumulated result and the
  new instruction as params. It produces a new result and
  constructs the sequence of visited coordinates."
  [result instruction]
  (let [{:keys [position direction visited]} result
        {:keys [left-or-right distance]} instruction
        next-direction (next-direction direction left-or-right)
        next-position  (next-position
                         {:from position
                          :direction next-direction
                          :distance distance})]
    {:direction next-direction
     :position next-position
     :visited (into visited (get-visited position next-position))}))

(defn process-instructions
  "Process all provided instructions and returning the
  resulting map containing the final position, the current
   direction and visited coordinates."
  [instructions]
  (reduce process-instruction {:position [0 0] :direction :north :visited [[0 0]]} instructions))

(defn- parse2
  "Parses a string formatted as e.g. 'R4' or 'L34' into an internal
   format to make things more readable."
  [instructions]
  (-> (fn [instruction]
        {:left-or-right (if (= \R (first instruction)) :right :left)
         :distance (read-string (subs instruction 1))})
      (map instructions)))

(defn- parse
  "Parses a string formatted as e.g. 'R4' or 'L34' into an internal
   format to make things more readable."
  [instructions]
  (-> (fn [instruction]
        {:left-or-right (if (= \R (first instruction)) :right :left)
         :distance (read-string (subs instruction 1))})
      (map instructions)))

(defn input->instructions
  "Maps an input string into a vector into a seq of instructions."
  [input]
  (-> (clojure.string/split input #", ")
      (parse)))

(defn manhattan-distance
  "Calculates the manhattan distance between two coords."
  [[start-x start-y] [end-x end-y]]
  (+ (Math/abs (long (- start-x end-x)))
     (Math/abs (long (- start-y end-y)))))

(defn duplicates
  [xs]
  "Return all duplicates in order of appearance."
  (for [[id frequency] (frequencies xs)
        :when (> frequency 1)]
    id))

(defn blocks-to-easter-bunny-hq-pt-1
  "This function solves the first part of day 1. It takes an input string
  containing a set of instructions, e.g. 'R4, L4, L1', and returns the
  number of blocks according to the manhattan distance algorithm that
  Santa needs to travel to get to the easter bunny HQ. In this first part,
  the HQ location is at the final coordinate."
  [input]
  (-> (input->instructions input)
      (process-instructions)
      (get :position)
      (manhattan-distance [0 0])))

(defn blocks-to-easter-bunny-hq-pt-2
  "This function solves the second part of day 1. It takes an input string
  containing a set of instructions, e.g. 'R4, L4, L1', and returns the
  number of blocks according to the manhattan distance algorithm that
  Santa needs to travel to get to the easter bunny HQ. In this second part,
  the HQ location is at the first coordinate that was visited twice."
  [input]
  (let [path (:visited (process-instructions (input->instructions input)))
        dupliate-indexes (sort (map #(.indexOf path %) (duplicates path)))
        first-duplicate (get path (first dupliate-indexes))]
    (manhattan-distance [0 0] first-duplicate)))
