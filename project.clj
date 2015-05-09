(defproject cljlm-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [berkeley/nlp "1.0.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]]
  :repositories {"local" ~(str (.toURI (java.io.File. "local-maven")))}
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler cljlm-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
