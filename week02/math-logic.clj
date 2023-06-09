(def x 2)
(def y 3)
(def z 4)
(def a 2)
(def b 4)
(def c 3)

(defn exercise-math []
  (println (+ x y z))
  (println (* x y z))
  (println (/ x y z))
  (println (+ (* x x) (* 3 y) z))
  (println (+ (int (Math/pow z x)) (int (Math/pow x z))))
  (println (+ (/ z y) x))
  (println (+ (* x y z) 1))
  (println (- (* x y z) 1)))

(defn exercise-logic []
  (println (> a b))
  (println (> b a))
  (println (< a b))
  (println (< b c))
  (println (>= c c))
  (println (= a c))
  (println (<= a b))
  (println (and (< a b) (> b c)))
  (println (or (< a b) (> b c))))