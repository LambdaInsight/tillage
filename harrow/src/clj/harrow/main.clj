(ns harrow.main
  (:require
    ; harrow
    [harrow.util          :as util          ]
    [harrow.http          :as http          ]
    ; external
    [clojure.core.async     :refer
      [ alts! chan go thread timeout
       >! >!! <! <!! go-loop ]              ]
    [clojure.tools.logging  :as log         ]
    [cheshire.core          :as ches        ] )
  (:import
    [clojure.core.async.impl.channels         ManyToManyChannel                     ]
    [clojure.lang                             PersistentHashMap PersistentArrayMap  ])
  (:gen-class))

(def blocking-producer >!!)
(def blocking-consumer <!!)

(def non-blocking-producer >!)
(def non-blocking-consumer <!)

(defn -main
  [& args]
  (log/info "Starting up...")
  (let [
        ^PersistentHashMap  config            (util/read-config "conf/app.edn")
                            http-port         8080                                    ]
    (cond
      (contains? config :ok)
        (do
          (log/debug config)
          (log/info "config [ok]")
          (log/info (str "http server is starting on " "http://localhost:" http-port))
          (http/start-http-server http-port))
      :else
        ;; exit 1 here
        (do
          (log/error config)
          (log/error "config [error]")
          (util/exit 1)))))
;;END
