(ns day-1.core-test
  (:require [clojure.test :refer :all]
            [day-1.core :refer :all]))

(deftest manhattan-distance-test
  (testing "FIXME, I fail."
    (is (= (manhattan-distance [0 0] [2 2]) 4))
    (is (= (manhattan-distance [0 0] [1 5]) 6))))

(def puzzle-input
  "R4, R5, L5, L5, L3, R2, R1, R1, L5, R5, R2, L1, L3, L4, R3, L1, L1, R2, R3, R3, R1, L3, L5, R3, R1, L1, R1, R2, L1, L4, L5, R4, R2, L192, R5, L2, R53, R1, L5, R73, R5, L5, R186, L3, L2, R1, R3, L3, L3, R1, L4, L2, R3, L5, R4, R3, R1, L1, R5, R2, R1, R1, R1, R3, R2, L1, R5, R1, L5, R2, L2, L4, R3, L1, R4, L5, R4, R3, L5, L3, R4, R2, L5, L5, R2, R3, R5, R4, R2, R1, L1, L5, L2, L3, L4, L5, L4, L5, L1, R3, R4, R5, R3, L5, L4, L3, L1, L4, R2, R5, R5, R4, L2, L4, R3, R1, L2, R5, L5, R1, R1, L1, L5, L5, L2, L1, R5, R2, L4, L1, R4, R3, L3, R1, R5, L1, L4, R2, L3, R5, R3, R1, L3")

(deftest blocks-to-easter-bunny-hq-pt-1-test
  (testing "how many blocks to easter bunny hq, pt 1"
    (is (= 250 (blocks-to-easter-bunny-hq-pt-1 puzzle-input)))))

(deftest blocks-to-easter-bunny-hq-pt-2-test
  (testing "how many blocks to easter bunny hq, pt 2"
    (is (= 151 (blocks-to-easter-bunny-hq-pt-2 puzzle-input)))))