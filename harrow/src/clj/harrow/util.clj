(ns harrow.util
  (:require
    ; harrow
    ; external
    [clojure.edn            :as     edn    ]
    [clojure.tools.logging  :as     log    ]
    [clojure.reflect        :as     refl   ] )
  (:import
    [java.io    File ByteArrayOutputStream
                ObjectOutputStream          ]
    [java.util  UUID                        ]) )

(defn read-file
  "Returns {:ok string } or {:error...}"
  [^String file]
  (try
    (cond
      (.isFile (File. file))
        {:ok (slurp file) }                         ; if .isFile is true {:ok string}
      :else
        (throw (Exception. "Input is not a file"))) ;the input is not a file, throw exception
  (catch Exception e
    {:error "Exception" :fn "read-file" :exception (.getMessage e) }))) ; catch all exceptions

(defn parse-edn-string
  "Returns the Clojure data structure representation of s"
  [s]
  (try
    {:ok (edn/read-string s)}
  (catch Exception e
    {:error "Exception" :fn "parse-config" :exception (.getMessage e)})))

(defn read-config
  "Returns the Clojure data structure version of the config file"
  [file]
  (let
    [ file-string (read-file file) ]
    (cond
      (contains? file-string :ok)
        ;this return the {:ok} or {:error} from parse-edn-string
        (parse-edn-string (file-string :ok))
      :else
        ;the read-file operation returned an error
        file-string)))

(defn exit [n]
  (log/info "process :: stop")
  (System/exit n))

(defn uuid
  "Returns a new java.util.UUID as string"
  []
  (str (UUID/randomUUID)))

(defn all-methods
  [java-object]
  (->> java-object refl/reflect
    :members
    (filter :return-type)
    (map :name)
    sort
    (map #(str "." %) )
    distinct
    println))

(def cwd
  (-> (java.io.File. "") .getAbsolutePath))

(defn serialize-bytes
  "Serializing input to byte array"
  ^bytes [input]
  (let [buffer (ByteArrayOutputStream.)]
    (with-open [ output-stream (ObjectOutputStream. buffer)]
      (.writeObject output-stream input))
    (.toByteArray buffer)))


