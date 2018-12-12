(ns harrow.core-test
  (:require
    [clojure.test :refer :all]
    [harrow.http :as http]
    [harrow.util :as util] ) )

;; util/read-file
(deftest read-file-dev-null
  (testing "[reading /dev/null]"
    (is
      (=
        (util/read-file "/dev/null")
        {:error "Exception" :fn "read-file" :exception "Input is not a file" }))))

(deftest read-file-conf
  (testing "[reading a file]"
    (is
      (=
       (contains? (util/read-file "conf/app.edn") :ok)
        true))))

;; util/parse-edn-string
(deftest parse-edn-string-simple
  (testing "[parsing string into edn]"
    (is
      (=
        (:ok (util/parse-edn-string "{:ok :ok}"))
        {:ok :ok}))))

(deftest parse-edn-string-err
  (testing "[parsing string into edn]"
    (is
      (=
        (util/parse-edn-string "1.4.21")
        {:error "Exception", :fn "parse-config", :exception "Invalid number: 1.4.21"}))))

