(ns day-4.pt2-test
  (:use [day-4.pt1 :only [valid-rooms]])
  (:require [clojure.test :refer :all]
            [day-4.pt2 :refer :all]
            [clojure.java.io :as io]))

(def input-string
  "This value is fed to the respective function in
  core.clj"
  (-> (io/resource "input.txt")
      (io/file)
      (slurp)))

(def id-by-rooms (valid-rooms input-string))

(deftest next-char-test
  (testing "next char test"
    (is (= \g (next-char 1 \f)))
    (is (= \c (next-char 3 \z)))
    (is (= \space (next-char 4 \-)))))

(deftest decrypt-name-test
  (testing "decryption time!"
    (is (= "very encrypted name" (decrypt-name "qzmt-zixmtkozy-ivhz" 343)))))

(println (real-name-search->id id-by-rooms "asdf"))