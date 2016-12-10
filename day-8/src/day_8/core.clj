(ns day-8.core)

(defn turn-on
  "Lits the pixel at the given coordinate"
  [screen [x y]]
  (assoc screen y (assoc (nth screen y) x \#)))

(defn insert-rect
  "Inserts a rectangle in the top left corner of the screen."
  [command screen]
  (let [width (:width command)
        height (:height command)
        coords (into [] (for [x (range 0 width) y (range 0 height)] [x y]))]
    (reduce
      (fn [result current]
        (turn-on result current))
      screen
      coords)))

(defn rotate-matrix-row
  "Rotates a matrix row."
  [index steps matrix]
  (let [row (get matrix index)
        size (count row)
        rotated (into [] (concat
                           (drop (- size steps) row)
                           (take (- size steps) row)))]
    (assoc matrix index rotated)))

(defn rotate-column
  "Rotates a column in the screen according to the command"
  [command screen]
  (->> screen
       (apply mapv vector)
       (rotate-matrix-row (:index command) (:steps command))
       (apply mapv vector)))

(defn handle
  "Command handler."
  [screen command]
  (case (:type command)
    :rect (insert-rect command screen)
    :rotate-row (rotate-matrix-row (:index command) (:steps command) screen)
    :rotate-column (rotate-column command screen)))

(defn command-type
  "Determines the command type."
  [string]
  (cond
    (clojure.string/starts-with? string "rect") :rect
    (clojure.string/starts-with? string "rotate row") :rotate-row
    (clojure.string/starts-with? string "rotate column") :rotate-column))

(defn string->rect-command
  "Converts a string to a rect command."
  [string]
  (-> string
      (clojure.string/split #" ")
      (second)
      (clojure.string/split #"x")
      (as-> size
            {:type :rect
             :width (read-string (first size))
             :height (read-string (second size))})))

(defn string->rotate-command
  [string]
  "Converts a string to a rotate command."
  (-> string
      (clojure.string/split #"(y|x)=")
      (second)
      (clojure.string/split #" by ")
      (as-> rotation
            {:type (if (.contains string "row") :rotate-row :rotate-column)
             :index (read-string (first rotation))
             :steps (read-string (second rotation))})))

(defn string->command
  "Converts a string to a command."
  [string]
  (case (command-type string)
        :rect (string->rect-command string)
        (string->rotate-command string)))

(defn new-screen "Creates a new screen." []
  (into []
        (for [row-index (range 0 6)]
          (mapv conj (take 50 (iterate conj \.))))))

(defn apply-instructions
  "Applies all instructions withing the lines
  on the screen. Use this to solve part two."
  [lines]
  (reduce
    (fn [screen string]
      (println string)
      (handle screen (string->command string)))
    (new-screen)
    lines))

(defn count-applied-instructions
  "Use this to solve part one"
  [screen]
  (reduce + 0 (map #(count (filter #{\#} %)) screen)))