(ns logic.room
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

;;http://www.puzzles.com/Projects/LogicProblems/RoomWithAView/download.pdf

(defn clueso [relo q]
  (fresh [a1 a2 a3 a4]
         (== q [a1 a2 a3 a4])
         (relo a1)
         (relo a2)
         (relo a3)
         (relo a4)))

(defn rooms [q]
  (fresh [h1 h2 h3 h4 w1 w2 w3 w4 l1 l2 l3 l4 l5 c1 c2 c3 c4 c5 r1 r2 r3 r4 r5]
         (== q [[h1 w1 l1 c1 r1]
                [h2 w2 l2 c2 r2]
                [h3 w3 l3 c3 r3]
                [h4 w4 l4 c4 r4]])

         (== [h1 h2 h3 h4] [:Ed :Fred :Ned :Ted])
         
         (membero :Alicia [w1 w2 w3 w4])
         (membero :Maria  [w1 w2 w3 w4])
         (membero :Patty  [w1 w2 w3 w4])
         (membero :Sara   [w1 w2 w3 w4])

         (membero :Baker  [l1 l2 l3 l4])
         (membero :Mann   [l1 l2 l3 l4])
         (membero :Sharp  [l1 l2 l3 l4])
         (membero :Watch  [l1 l2 l3 l4])

         (membero :Atlanta       [c1 c2 c3 c4])
         (membero :Chicago       [c1 c2 c3 c4])
         (membero :Indianapolis  [c1 c2 c3 c4])
         (membero :Phoenix       [c1 c2 c3 c4])

         (membero :bedroom     [r1 r2 r3 r4])
         (membero :family-room [r1 r2 r3 r4])
         (membero :kitchen     [r1 r2 r3 r4])
         (membero :living-room [r1 r2 r3 r4])
         

         ;; 1. The show did a makeover on Ted’s kitchen.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== h :Ted) (== r :kitchen)]
                           [(!= h :Ted)])))
                 q)
         ;; Ed wasn’t married to Sara but he lived in Chicago.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== h :Ed) (== c :Chicago) (!= w :Sara)]
                           [(!= h :Ed)])))
                 q)
         ;; Ned’s last name wasn’t Mann.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== h :Ned) (!= l :Mann)]
                           [(!= h :Ned)])))
                 q)


         ;; 2. The couple who remade their living room lived in Atlanta.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== r :living-room) (== c :Atlanta)]
                           [(!= r :living-room)])))
                 q)

         ;; 3. Fred, whose last name wasn’t Sharp, was married to Patty.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== h :Fred) (!= l :Sharp) (== w :Patty)]
                           [(!= h :Fred)])))
                 q)
         
         ;; 4. The Watch couple lived in Phoenix but didn’t makeover their family room.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== l :Watch) (!= r :family-room) (== c :Phoenix)]
                           [(!= l :Watch)])))
                 q)
         
         ;; 5. Ted wasn’t married to Maria.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== h :Ted) (!= w :Maria)]
                           [(!= h :Ted)])))
                 q)
         ;; Alicia Baker didn’t have her living room remade.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== w :Alicia) (== l :Baker) (!= r :living-room)]
                           [(!= w :Alicia)])))
                 q)

         ;; 6. The Sharp couple had their bedroom redone, but they weren’t located in Indianapolis.
         (clueso (fn [q]
                   (fresh [h w l c r]
                          (== q [h w l c r])
                          (conde
                           [(== l :Sharp) (== r :bedroom) (!= c :Indianapolis)]
                           [(!= l :Sharp)])))
                 q)))


(defn test-rooms []
  (let [result
        (run* [q] (rooms q))]
    (println "Results:" (count result))
    (println "First:" (first result))))


(comment (test-pigs))

