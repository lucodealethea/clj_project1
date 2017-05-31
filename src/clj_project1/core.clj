(ns clj_project1.core)

(defn example-handler [request]
  {:body "Hello Clojure Delicate World !"})
(defn on-init []
  (println "Initializing this sample webapp !"))
(defn on-destroy []
  (println "Destroying sample webapp!"))
