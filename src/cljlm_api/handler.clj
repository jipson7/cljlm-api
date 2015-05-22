(ns cljlm-api.handler
  (:import [edu.berkeley.nlp.lm.io LmReaders])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


;Reads a binary language model
(defn load-lm-binary [binary]
  (LmReaders/readLmBinary binary))

;Reads a language model made with the google books data set
(defn load-lm-google [binary vocab]
  (LmReaders/readGoogleLmBinary binary vocab))

;Provides access to Java Map that allows checking of raw counts
(defn load-lm-map [binary vocab]
  (LmReaders/readNgramMapFromBinary binary vocab))


;GIves a logarthmic probability of a sentence given the language model. Sentence is passed as a single string
(defn compute-log-prob [lm sentence]
  (let [array (.split (.trim sentence) "\\s+")
        words (java.util.Arrays/asList array)]
    (.scoreSentence lm words)))


(def test-binary "language-models/google/english/eng.blm")
(def test-vocab "language-models/google/english/vocab_cs")
(def test-sentence "this is a test")
(def language-model (load-lm-google test-binary test-vocab))

(defn test-lm []
    (format "%s = %.2f" sentence (compute-log-prob language-model test-sentence)))

(defroutes app-routes
  (GET "/" [] (test-lm))
  (GET "/:sentence" [sentence] 
    (pr-str (compute-log-prob language-model (str sentence))))
  (route/not-found "Not Found"))


(def app
  (wrap-defaults app-routes site-defaults))
