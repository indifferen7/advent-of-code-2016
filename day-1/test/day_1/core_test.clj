(ns day-1.core-test
  (:require [clojure.test :refer :all]
            [day-1.core :refer :all]))

(deftest manhattan-distance-test
  (testing "Manhattan distance test"
    (is (= (manhattan-distance [0 0] [2 2]) 4))
    (is (= (manhattan-distance [0 0] [1 5]) 6))))