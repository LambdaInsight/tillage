(defproject harrow "0.1.0"
  :description "Small web service for streamlining cloud deployments"
  :url "https://too.good"
  :license {:name "AGPL"
            :url "https://www.gnu.org/licenses/agpl-3.0.en.html"}
  :source-paths       ["src/clj"]
  :java-source-paths ["src/java"]
  :aot :all
  :dependencies [
    [org.clojure/clojure                        "1.9.0"      ]
    [org.clojure/core.async                     "0.4.490"         ]
    [org.clojure/tools.logging                  "0.4.1"           ]
    [log4j/log4j                                "1.2.17"          ]
    [cheshire                                   "5.8.1"           ]
    [com.taoensso/tufte                         "2.0.1"
      :exclusions [
        org.clojure/tools.reader
      ]                                                           ]
    [compojure                                  "1.6.1"           ]
    [ring/ring-json                             "0.4.0"           ]
    [http-kit                                   "2.3.0"           ]
  ]
  :exclusions [
    javax.mail/mail
    javax.jms/jms
    com.sun.jdmk/jmxtools
    com.sun.jmx/jmxri
    jline/jline
    org.clojure/clojure
  ]
  :profiles {
    :uberjar {
      :aot :all
    }
  }
 :jvm-opts [
   "-Xms256m" "-Xmx1024m" "-server"
   "-XX:+UseG1GC" "-XX:+UseNUMA"
   "-XX:MaxGCPauseMillis=300"
   "-XX:+UseTLAB" "-XX:+UseStringDeduplication"
   "-Dcom.sun.management.jmxremote"
   "-Dcom.sun.management.jmxremote.local.only=false"
   "-Dcom.sun.management.jmxremote.authenticate=false"
   "-Dcom.sun.management.jmxremote.ssl=false"
  ]
  :main harrow.main)
