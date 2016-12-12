(ns day-10.core)

(defn giver "Extracts a giver bot from a line."
  [line]
  (first (clojure.string/split line #" gives ")))

(defn high-recipient
  "Extracts the high recipient from a line."
  [line]
  (second (clojure.string/split line #"and high to ")))

(defn low-recipient
  "Extracts the low recipient from a line."
  [line]
  (-> (clojure.string/split line #" gives low to ")
      (second)
      (clojure.string/split #" and ")
      (first)))

(defn parse-rule-line
  "Parses a rule line to an internal format."
  [line]
  {(giver line) {:giver (giver line)
                 :low-recipient (low-recipient line)
                 :high-recipient (high-recipient line)}})

(defn create-rules
  "Creates all rules from a seq of lines."
  [lines]
  (reduce
    (fn [result line]
      (if (not (clojure.string/starts-with? line "bot"))
        result
        (into result (parse-rule-line line))))
    {}
    lines))

(defn parse-init-line
  "Parses an init line, extracting the bot and value parts from it
  in a somewhat ugly way."
  [line]
  (let [bot (second (clojure.string/split line #" goes to "))
        value (read-string
                (second (clojure.string/split
                          (first (clojure.string/split line #" goes to ")) #"value ")))]
    [bot value]))

(defn put-value
  "Adds or sets a bot value in the state map."
  [state bot value]
  (if (contains? state bot)
    (assoc state bot (sort (conj (get state bot) value)))
    (into state {bot [value]})))

(defn give-to-receiver
  "The giver gives a the provided value to the provided receiver."
  [state giver receiver value]
  (-> state
      (assoc giver (into [] (remove #{value} (get state giver))))
      (put-value receiver value)))

(defn create-initial-state
  "Creates the initial state from a seq of lines."
  [lines]
  (reduce
    (fn [result line]
      (if (not (clojure.string/starts-with? line "value"))
        result
        (let [[bot value] (parse-init-line line)]
          (put-value result bot value))))
    {}
    lines))

(defn transfer
  "Returns the new state after the rule has been applied
  to the state."
  [state rule]
  (let [{:keys [giver low-recipient high-recipient]} rule
        [low high] (get state giver)]
    (if (and (= low 17) (= high 61))
      (println "Answer in part one is:" giver))
    (-> state
        (give-to-receiver giver high-recipient high)
        (give-to-receiver giver low-recipient low))))

(defn apply-rules-on-state
  "Applies all given rules on the state. Returns the new
  state and outputs the solution to part one in stdout."
  [rules state]
  (loop [new-state state
         remaining (keys state)]
    (if (empty? remaining)
      new-state
      (let [bot (first remaining)]
        (if (= 2 (count (get new-state bot)))
          (recur (transfer new-state (get rules bot)) (keys new-state))
          (recur new-state (rest remaining)))))))

(defn solution-part-two
  "This function solves part two."
  [rules state]
  (let [result (apply-rules-on-state rules state)]
    (reduce * (flatten (vals (select-keys result ["output 0" "output 1" "output 2"]))))))