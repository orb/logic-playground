(ns logic.core
  (:use [clojure.core.logic]))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))

(defn pairo [p]
  (fresh [a d]
    (== (lcons a d) p)))

(defn listo [l]
  (conde
    [(emptyo l) s#]
    [(pairo l)
     (fresh [d]
       (resto l d)
       (listo d))]))