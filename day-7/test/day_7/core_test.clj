(ns day-7.core-test
  (:require [clojure.test :refer :all]
            [day-7.core :refer :all]
            [clojure.java.io :as io]))

(deftest ip-abba-compliant?-test
  (testing "da IPs"
    (is (ip-abba-compliant? "abba[mnop]qrst"))
    (is (not (ip-abba-compliant? "abcd[bddb]xyyx")))
    (is (not (ip-abba-compliant? "aaaa[qwer]tyui")))
    (is (ip-abba-compliant? "ioxxoj[asdfgh]zxcvbn"))))