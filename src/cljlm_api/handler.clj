(ns cljlm-api.handler
  (:import [edu.berkeley.nlp.lm.io LmReaders])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure.handler :as handler]))


;Reads a binary language model
(defn load-lm-binary [binary]
  (LmReaders/readLmBinary binary))

;Reads a language model made with the google books data set
(defn load-lm-google [binary vocab]
  (LmReaders/readGoogleLmBinary binary vocab))


;Provides access to Java Map that allows checking of raw counts
(defn load-lm-map [binary vocab]
  (LmReaders/readNgramMapFromBinary binary vocab))


;Gives a logarthmic probability of a sentence given the language model. Sentence is passed as a single string
;Use With load-lm-binary
(defn compute-log-prob [lm sentence]
  (let [array (.split (.trim sentence) "\\s+")
        words (java.util.Arrays/asList array)]
    (.scoreSentence lm words)))

;Same as above, use with load-lm-google
(defn get-log-prob [lm sentence]
  (let [array (.split (.trim sentence) "\\s+")
        words (java.util.Arrays/asList array)]
    (.getLogProb lm words)))

(defn console-log [sentence prob] (println sentence " " prob) "response")

;Test data for now
;TODO write actual testing code or run the code present in the berkeleylm tests
(def test-binary "language-models/google/english/eng.blm")
(def test-vocab "language-models/google/english/vocab_cs")
(def test-sentence "this is a test")
(def language-model (load-lm-google test-binary test-vocab))

(defn test-lm []
    (format "%s = %.2f" test-sentence (get-log-prob language-model test-sentence)))


;Middleware function to allow CORS request from chrome extension
(defn allow-cross-origin  
    "middleware function to allow crosss origin"  
    [handler]  
    (fn [request]  
        (let [response (handler request)]  
        (assoc-in response [:headers "Access-Control-Allow-Origin"] "*"))))

(defroutes app-routes
  (GET "/" [] (test-lm))
  (GET "/:sentence" [sentence] (let [prob (get-log-prob language-model (str sentence))]
    (do (console-log sentence prob) (pr-str prob))))
  (route/not-found "Not Found"))


(def app
  (-> (wrap-defaults app-routes site-defaults) (allow-cross-origin)))
