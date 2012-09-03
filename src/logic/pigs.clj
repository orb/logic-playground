(ns logic.pigs
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

;;http://holidays.hobbyloco.com/halloween/logic1.html


;; Petey Pig did not hand out popcorn.
(defn clue1 [q]
  (fresh [p h t]
         (== q [p h t])
         (conde [(== p :petey) (!= t :popcorn)]
                [(!= p :petey)])))
;; Pippin Pig does not live in the wood house.
(defn clue2 [q] 
  (fresh [p h t]
         (== q [p h t])
         (conde [(== p :pippin) (!= h :wood)]
                [(!= p :pippin)])))

;; The pig that lives in the straw house, handed out popcorn.
(defn clue3 [q]
  (fresh [p h t]
         (== q [p h t])
         (conde [(== h :straw) (== t :popcorn)]
                [(!= h :straw)])))

;; Petunia Pig handed out apples.
(defn clue4 [q]
  (fresh [p h t]
         (== q [p h t])
         (conde [(== p :petunia) (== t :apple)]
                [(!= p :petunia)])))

;; The pig who handed out chocolate, does not live in the brick house.
(defn clue5 [q]
  (fresh [p h t]
         (== q [p h t])
         (conde [(== t :chocolate) (!= h :brick)]
                [(!= t :chocolate)])))


(defn clueso [relo q]
  (fresh [a1 a2 a3]
         (== q [a1 a2 a3])
         (relo a1)
         (relo a2)
         (relo a3)))

(defn pigs [q]
  (fresh [p1 p2 p3 h1 h2 h3 t1 t2 t3]
         (== q [[p1 h1 t1] [p2 h2 t2] [p3 h3 t3]])

         (== [p1 p2 p3] [:petey :pippin :petunia])
         (membero :straw [h1 h2 h3])
         (membero :wood [h1 h2 h3])
         (membero :brick [h1 h2 h3])

         (membero :apple [t1 t2 t3])
         (membero :popcorn [t1 t2 t3])
         (membero :chocolate [t1 t2 t3])

         (clueso clue1 q)
         (clueso clue2 q)
         (clueso clue3 q)
         (clueso clue4 q)
         (clueso clue5 q)
))


(defn test-pigs []
  (let [result
        (run* [q] (pigs q))]
    (println "Results:" (count result))
    (println "First:" (first result))))


(comment (test-pigs))

