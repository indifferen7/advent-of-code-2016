(ns day-6.core-test
  (:require [clojure.test :refer :all]
            [day-6.core :refer :all]
            [clojure.java.io :as io]))

(def sample-input
  "eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar")

(def input-string
  "This value is fed to the freq function in core.clj to complete the challenge."
  (-> (io/resource "input.txt")
      (io/file)
      (slurp)))

(deftest max-freq-for-line-test
  (testing "max freq test."
    (is (= "easter" (freq (clojure.string/split-lines sample-input) first)))))