(defn gcd [num1 num2] ;; takes two parameters
  (if (= 0 (rem num1 num2))
    num2 ;; if num1 % num2 == 0, return num2
    (gcd num2 (rem num1 num2)))) ;; else find gcd of num2 and (num1 % num2)

(gcd 480 1001) ;; finds the greatest common denominator of 480 and 1001 (1)

;; user=> (gcd 12 8)
;; 4
;; user=> (gcd 15 105)
;; 15
;; user=> (gcd 15 100)
;; 5
;; user=> (gcd 480 1001)
;; 1