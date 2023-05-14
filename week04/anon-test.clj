;; Anonymous Function Guide

(def items-list '(0 1 2 3))

(println items-list)

(println (map (fn [item] (+ 2 item)) items-list)) ;; (2 3 4 5)

(println (map #(+ 2 %) items-list)) ;; (2 3 4 5)


;; Basically the # function takes the fn function a step further. In addition to not giving the function a name, you don't even give the parameters names! Instead the parameters are %1, %2, %3, etc. (You can use % if it's the only parameter and %& to mean any additional parameters.) Check out the docs for more here: https://clojure.org/guides/learn/functions#_anonymous_function_syntaxLinks to an external site.

;; You might use an anonymous function to do something super simple like using it in a map, filter, or reduce function. This is because solutions like these don't work:

;; (map + 2 item items-list) ;; nope - map only takes 2 parameters: the function to apply and the collection to apply it to.

;; (map (+ 2 item) items-list) ;; nope - how is the program supposed to know what 'item' is? Besides, map would receive the return value from the + function, not the + function itself.

;; So that's why you'd want to use an anonymous function here. If you wanted a named function, however, you could do that:

(defn add-two [item]

  (+ 2 item))

(println (map add-two items-list)) ;; (2 3 4 5)
;; but that's a little clunkier so it's just best to make it anonymous.