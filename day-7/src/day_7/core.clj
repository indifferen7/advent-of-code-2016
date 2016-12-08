(ns day-7.core)

(defn extract-parts
  "Extract ip parts, dividing them into two buckets."
  [ip]
  (let [parts (clojure.string/split ip #"[\[\]]")]
    {:within-brackets (take-nth 2 (drop 1 parts))
     :outside-brackets (take-nth 2 parts)}))

; part one stuff here

(defn part-abba-compliant?
  "Determines if a part, either outside or within brackets,
  is abba compliant according to the challenge."
  [part]
  (true?
     (reduce
       (fn [prev next]
         (let [candidate (clojure.string/join (into [prev next] [next prev]))]
           (if (or (= prev next) (not (.contains part candidate)))
             next
             (reduced true))))
       (first part)
       (rest part))))

(defn ip-abba-compliant?
  "Determines if an ip is abba compliant."
  [ip]
  (let [{:keys [within-brackets outside-brackets]} (extract-parts ip)]
    (and
      (not-any? true? (map part-abba-compliant? within-brackets))
      (true? (some true? (map part-abba-compliant? outside-brackets))))))

(defn count-abba-compliant
  "Use this to solve part one of the challenge. This function
  eats lines from \n  input.txt."
  [lines]
  (count (filter ip-abba-compliant? lines)))

;part two stuff here

(defn abas
  "Retrieves all abas from the part."
  [part]
  (reduce
    (fn [state current]
      (let [{:keys [prev result]} state
            candidate (clojure.string/join [prev current prev])]
        (if (and (not (= prev current)) (.contains part candidate))
          {:result (conj result candidate) :prev current}
          {:result result :prev current})))
    {:prev (first part) :result []}
    (rest part)))

(defn aba->bab "Aba to bab."
  [aba]
  (clojure.string/join [(nth aba 1) (nth aba 0) (nth aba 1)]))

(defn part-aba-compliant?
  "Determines if a part is aba compliant."
  [within-brackets outside-brackets-part]
  (let [abas (:result (abas outside-brackets-part))
        candidate-babs (map aba->bab abas)]
    (reduce (fn [result current]
              (if (empty? (filter #(.contains % current) within-brackets))
                false
                (reduced true)))
            false
            candidate-babs)))

(defn ip-aba-compliant?
  "Determines if an ip is aba compliant."
  [ip]
  (let [{:keys [within-brackets outside-brackets]} (extract-parts ip)]
    (true? (some true?
                 (map (partial part-aba-compliant? within-brackets) outside-brackets)))))

(defn count-aba-compliant
  "Use this to solve part two. This function eats lines from
  input.txt."
  [lines]
  (count (filter ip-aba-compliant? lines)))