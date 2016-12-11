(ns day-9.core-test
  (:require [clojure.test :refer :all]
            [day-9.core :refer :all]
            [clojure.java.io :as io]))

(deftest process-next-test
  (testing "processing da text"
    (is (= "ABABCD" (clojure.string/join (process-pt1 "(2x2)ABCD"))))
    (is (= "ABCD" (clojure.string/join (process-pt1 "(1x1)ABCD"))))
    (is (= "ABBBBBC" (clojure.string/join (process-pt1 "A(1x5)BC"))))
    (is (= "XYZXYZXYZ" (clojure.string/join (process-pt1 "(3x3)XYZ"))))
    (is (= "(1x3)A" (clojure.string/join (process-pt1 "(6x1)(1x3)A"))))
    (is (= "X(3x3)ABC(3x3)ABCY" (clojure.string/join (process-pt1 "X(8x2)(3x3)ABCY"))))))