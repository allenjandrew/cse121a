(def flavors1 '("vanilla" "chocolate" "cherry-ripple"))
(def flavors2 '("lemon" "butterscotch" "licorice-ripple"))
(def combined (distinct (concat flavors1 flavors2)))

(defn cartesian-product
  ([] '(()))
  ([xs & more]
   (mapcat #(map (partial cons %)
                 (apply cartesian-product more))
           xs)))
;; (cartesian-product '(a b c) [1 2 3]) ;; testing

(defn has-chocolate? [col]
  (some #{"chocolate"} col))

(defn -main []
  (println "original menu results")
  (println (filter has-chocolate? (cartesian-product flavors1 flavors2)))
  ;; has-chocolate? does the same thing as #(some #{"chocolate"} %)
  (println "modified menu results")
  (println (filter #(some #{"chocolate"} %) (cartesian-product combined combined))))

(-main)