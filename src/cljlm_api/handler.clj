(ns cljlm-api.handler
  (:import [edu.berkeley.nlp.lm.io LmReaders])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def test-binary "google.binary")
(def test-sentence "this is a test")

(defn load-lm [binary]
  (LmReaders/readLmBinary binary))

(defn compute-log-prob [lm sentence]
  (let [array (.split (.trim sentence) "\\s+")
        words (java.util.Arrays/asList array)]
    (.scoreSentence lm words)))

(defn test-lm []
  (let [lm (load-lm test-binary)
        sentence test-sentence]
    (format "%s = %.2f" sentence (compute-log-prob lm sentence))))

(defroutes app-routes
  (GET "/" [] (test-lm))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
