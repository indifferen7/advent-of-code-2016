(ns day-3.core-test
  (:require [clojure.test :refer :all]
            [day-3.core :refer :all]
            [clojure.java.io :as io]))

(def input-string
  "This value is fed to the respective function in
  core.clj"
  (-> (io/resource "input.txt")
      (io/file)
      (slurp)))

(deftest valid-triangle-test
  (testing "valid triangle test"
    (is (not (valid-triangle [5 10 25])))
    (is (valid-triangle [5 10 7]))))