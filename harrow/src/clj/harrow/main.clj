(ns harrow.main
  (:require
    ; harrow
    [harrow.util            :as util   ]
    [harrow.exp-1           :as exp-1  ]
    [harrow.exp-2           :as exp-2  ]
    [harrow.exp-3           :as exp-3  ]
    [harrow.exp-4           :as exp-4  ]
    ; external
    [clojure.tools.logging  :as log    ] 
    [criterium.core         :as crit   ] )
  (:import
    [clojure.lang PersistentHashMap PersistentArrayMap ] )
  (:gen-class))

(defn -main
  [& args]
  (log/info "Starting up...")
  (let  [ 
          ^PersistentHashMap  config            (util/read-config "conf/app.edn") 
                              iterations        100000
        ]
    (cond
      (contains? config :ok)
        (do
          (log/debug config)
          (log/info "config [ok]")
          (log/info "Experiment 1:")
          (println (crit/quick-bench (exp-1/pi iterations)))
          (println (crit/bench (exp-1/pi iterations)))
          (log/info "Experiment 2:")
          (println (crit/quick-bench (nth exp-2/pi-seq iterations)))
          (println (crit/bench (nth exp-2/pi-seq iterations)))
          (log/info "Experiment 3:")
          (println (crit/quick-bench (exp-3/pi-estimate iterations)))
          (println (crit/bench (exp-3/pi-estimate iterations)))
          (log/info "Experiment 4:")
          (println (crit/quick-bench (exp-4/pi-estimate iterations)))
          (println (crit/bench (exp-4/pi-estimate iterations)))
          (log/info "The end"))
      :else
        ;; exit 1 here
        (do
          (log/error config)
          (log/error "config [error]")
          (util/exit 1)))))
;;END
