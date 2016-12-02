(ns day-2.core-test
  (:require [clojure.test :refer :all]
            [day-2.core :refer :all]))

(def instructions "ULL\nRRDDD\nLURDL\nUUUUD")

(deftest instruction->key-test
  (testing "instruction->key test"
    (is (= 1 (instruction->key keypad-pt1 5 "ULL")))))

(deftest process-instructions-test
  (testing "process-instructions test"
    (is (= 1985 (read-string (process-instructions keypad-pt1 instructions))))))