;; I read through the example solution and didn't understand it. Then I spent an hour writing this code and realized it's almost identical to the example solution. But now I understand it lol.

(defn decode-prime-list
  ([rle-list] (decode-prime-list rle-list 2 []))
  ([rle-list counter new-vect]
   (let [firstnum (first rle-list) ;; let firstnum be the first number from the list
         next-count (+ counter (if (= firstnum 0) 1 firstnum)) ;; let adder = firstnum, unless it's 0, then make it 1
         newer-vect (if (= firstnum 1) (conj new-vect counter) new-vect)] ;; if firstnum is 1, add the prime to new-vect, else just return new-vect
     (if (empty? (rest rle-list))
       (seq newer-vect)
       (recur (rest rle-list) next-count newer-vect)))))
;; if there's nothing left in the list, return our answer as a list; else recur the function



;; The code from here and below was taken from the example solution.
(def primes '(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97))
(def rleprimes '(1 1 0 1 0 1 3 1 0 1 3 1 0 1 3 1 5 1 0 1 5 1 3 1 0 1 3 1 5 1 5 1 0 1 5 1 3 1 0 1 5 1 3 1 5 1 7 1 3))

(defn -main []
  (println rleprimes)
  (println primes)
  (println (= primes (decode-prime-list rleprimes))))