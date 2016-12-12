(ns day-10.core-test
  (:require [clojure.test :refer :all]
            [day-10.core :refer :all]
            [clojure.java.io :as io]))


(def input "Input from challenge goes here..")

(def rules (create-rules (clojure.string/split-lines input)))
(def initial-state (create-initial-state (clojure.string/split-lines input)))

; run this to solve part one
(apply-rules-on-state rules initial-state)

; run this to solve part two
(println (solution-part-two rules initial-state))