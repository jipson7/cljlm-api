(ns cljlm-api.handler
  (:import [edu.berkeley.nlp.lm.io LmReaders])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn load-lm [binary]
  (LmReaders/readLmBinary binary))

(defn compute-log-prob [lm sentence]
  (let [array (.split (.trim sentence) "\\s+")
        words (java.util.Arrays/asList array)]
    (.scoreSentence lm words)))


(def test-binary "google.binary")
(def test-sentence "this is a test")
(def language-model (load-lm test-binary))


(defn test-lm []
  (let [sentence test-sentence]
    (format "%s = %.2f" sentence (compute-log-prob language-model sentence))))

(defroutes app-routes
  (GET "/" [] (test-lm))
  (GET "/:sentence" [sentence] 
    (pr-str (compute-log-prob language-model (str sentence))))
  (route/not-found "Not Found"))


(def app
  (wrap-defaults app-routes site-defaults))
