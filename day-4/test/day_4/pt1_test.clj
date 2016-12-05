(ns day-4.pt1-test
  (:require [clojure.test :refer :all]
            [day-4.pt1 :refer :all]
            [clojure.java.io :as io]))

(def name-1 "aaaaa-bbb-z-y-x-123[abxyz]")
(def name-2 "a-b-c-d-e-f-g-h-987[abcde]")
(def name-3 "not-a-real-room-404[oarel]")
(def name-4 "totally-real-room-200[decoy]")

(println (valid-room name-1))

(deftest extract-checksum-test
  (testing "checksum extraction"
    (is (= "abxyz" (extract-checksum (seq "aaaaabbbzyx"))))
    (is (= "abcde" (extract-checksum (seq "abcdefgh"))))
    (is (= "oarel" (extract-checksum (seq "notarealroom"))))
    (is (not= "decoy" (extract-checksum (seq "totallyrealroom"))))))

(deftest valid-room-test
  (testing "tests a room name for its validity"
    (is (= 123 (second (valid-room name-1))))
    (is (= 987 (second (valid-room name-2))))
    (is (= 404 (second (valid-room name-3))))
    (is (nil? (second (valid-room name-4))))))

(def input-string
  "This value is fed to the respective function in
  core.clj"
  (-> (io/resource "input.txt")
      (io/file)
      (slurp)))