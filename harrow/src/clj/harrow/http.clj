(ns harrow.http
  (:require
    ; harrow
    ; external
    [clojure.tools.logging  :as log       ] )
  (:import
    [org.rapidoid.config    Conf              ]
    [org.rapidoid.setup     On OnRoute        ]
    [org.rapidoid.http.impl RouteOptions      ]
    [org.rapidoid.http      Req               ]
    [org.rapidoid.http      ReqHandler        ]
    [org.rapidoid.u         U                 ]
    [java.util              Map               ]) )

;;;;;;;;;;;;;;;;;;
(gen-class
  :name harrow.http.handler
  :prefix "handle-"
  :methods [
    ^:static [echo [org.rapidoid.http.Req] java.util.Map]
  ])

(defn handle-echo
  [req]
  (U/map (.headers req)))
;;;;;;;;;;;;;;;;;

(defn set-http-server-options
  [l]
  (let [http-conf (Conf/HTTP)]
    (doseq [pair l]
      (.set http-conf (first pair) (second pair)))))

(defn get-http-server-options
  []
  (.toString (Conf/HTTP)))

(defn set-http-get-route
  ^OnRoute [^String path]
  (.managed (On/get path) false))

(defn get-route-options
  ^RouteOptions [^OnRoute route]
  (.options route))

(defn config-handler
  []
  (U/map "ok" (.toMap (Conf/ROOT))))

(defn echo-handler
  []
  (reify ReqHandler
    (execute
      [this ^Req req]
      (U/map (.headers req)))))



