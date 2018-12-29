(ns harrow.http
  (:require
    ; harrow
    ; external
    [clojure.tools.logging  :as log       ]
    [compojure.core         :as dsl       ]
    [compojure.route        :as route     ]
    [ring.middleware.json   :as json      ]
    [ring.util.response     :as resp      ]
    [org.httpkit.server     :as server    ]
    ))


(defn get-user-by-id 
  [_] 
  :id)

(defn main-handler
  [req]
  (let [ resp { :status 200 
                :headers {"Content-Type" "text/html" "X-header" "!签!"} 
                :body "<h2>/</h2>"                             } ]
    (log/debug req)
    (log/debug resp)
    ;;return
    resp))

(defn echo-handler
  [req]
  (let [  json-response 
            (resp/response 
              { :ok
                {
                  :remote-addr         (:remote-addr req)          
                  :server-port         (:server-port req)          
                  :character-encoding  (:character-encoding req)   
                  :uri                 (:uri req)                  
                  :server-name         (:server-name req)          
                  :scheme              (:scheme req)
                }              
              } ) ]
    (log/debug req)
    (log/debug json-response)
    ;;return
    json-response))

(defn enable-routes
  []
  (dsl/defroutes all-routes

    (dsl/GET "/"      [] main-handler)
    (dsl/GET "/echo"  [] (json/wrap-json-response echo-handler))

    (route/files "/static/")
    (route/not-found "<p>Page not found.</p>")))

(defn start-http-server
  [http-port]
  (enable-routes)
  (server/run-server all-routes {:port http-port}))


