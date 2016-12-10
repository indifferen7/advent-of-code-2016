(ns day-8.core-test
  (:require [clojure.test :refer :all]
            [day-8.core :refer :all]
            [clojure.java.io :as io]))

(deftest string->command-test
  (testing "string->command test"
    (is (= {:type :rect :width 3 :height 2} (string->command "rect 3x2")))
    (is (= {:type :rotate-column :index 1 :steps 2} (string->command "rotate column x=1 by 2")))
    (is (= {:type :rotate-row :index 3 :steps 1} (string->command "rotate rows x=3 by 1")))))