;; This is a combat damage calculator for the game Polytopia. Combat formulas were found at https://polytopia.fandom.com/wiki/Combat and raw unit data was retrieved in-game.


;; Polytopia.Fandom.com formulas:
;; attackForce = attacker.attack * (attacker.health / attacker.maxHealth)
;; defenseForce = defender.defense * (defender.health / defender.maxHealth) * defenseBonus
;; totalDamage = attackForce + defenseForce
;; attackResult = round ((attackForce / totalDamage) * attacker.attack * 4.5)
;; defenseResult = round ((defenseForce / totalDamage) * defender.defense * 4.5)

;; Questions to ask the user:
;; attacker: unit-type? health? veteran? embarked? ship-type?
;; defender: unit-type? health? veteran? defended? fortified? embarked? ship-type? distance?


;; Define raw data for each unit type
(def unit-map {:warrior {:type "warrior" :max-health 10 :attack 2 :defense 2 :range 1 :fortify? true :can-be-vet? true}
               :rider {:type "rider" :max-health 10 :attack 2 :defense 1 :range 1 :fortify? true :can-be-vet? true}
               :archer {:type "archer" :max-health 10 :attack 2 :defense 1 :range 2 :fortify? true :can-be-vet? true}
               :defender {:type "defender" :max-health 15 :attack 1 :defense 3 :range 1 :fortify? true :can-be-vet? true}
               :swordsman {:type "swordsman" :max-health 15 :attack 3 :defense 3 :range 1 :fortify? true :can-be-vet? true}
               :catapult {:type "catapult" :max-health 10 :attack 4 :defense 0 :range 3 :fortify? false :can-be-vet? true}
               :knight {:type "knight" :max-health 10 :attack 3.5 :defense 1 :range 1 :fortify? true :can-be-vet? true}
               :giant {:type "giant" :max-health 40 :attack 5 :defense 4 :range 1 :fortify? false :can-be-vet? false}
               :cloak {:type "cloak" :max-health 5 :attack 0 :defense 0.5 :range 1 :fortify? false :can-be-vet? false}
               :dagger {:type "dagger" :max-health 10 :attack 2 :defense 2 :range 1 :fortify? false :can-be-vet? false}
               :bunny {:type "bunny" :max-health 20 :attack 5 :defense 1 :range 1 :fortify? false :can-be-vet? false}
               :bunta {:type "bunta" :max-health 20 :attack 5 :defense 1 :range 1 :fortify? false :can-be-vet? false}})
;; Define raw data for each ship type
(def ship-map {:boat {:type "boat" :attack 1 :defense 1 :range 2}
               :ship {:type "ship" :attack 2 :defense 2 :range 2}
               :battleship {:type "battleship" :attack 4 :defense 3 :range 2}
               :pirate {:type "pirate" :attack 2 :defense 2 :range 1}
               :dinghy {:type "dinghy" :attack 0 :defense 0.5 :range 1}})

;; (println (:max-health (:warrior unit-map)))
;; (println ((unit-map (keyword "warrior")) :max-health))
;; (println (ship-map :boat))
;; (println (unit-map :battleship))


(defn -main []
  (let [;; Ask user for attacker type
        attacker-type (do (println "Attacker: unit-type?") (read-line))
        ;; Get attacker data from unit-map
        attacker (unit-map (keyword attacker-type))
        ;; Ask user for attacker health
        attacker-health (do (println "health?") (Integer/parseInt (read-line)))
        ;; Get unit base max health from attacker data; ask user if veteran and if so, add 5
        attacker-maxhealth (+ (attacker :max-health) (if (attacker :can-be-vet?) (do (println "veteran? y/n") (if (= (read-line) "y") 5 0)) 0))
        ;; Ask user if unit is embarked; if so, ask for ship type (cloaks can only be dinghys, and daggers can only be pirates), and get data from ship-map; otherwise, keep using unit-map data
        attacker-stats (do (println "embarked? y/n") (if (= (read-line) "y") (cond (= attacker-type "cloak") (ship-map :dinghy) (= attacker-type "dagger") (ship-map :pirate) :else (do (println "ship-type?") (ship-map (keyword (read-line))))) attacker))
        ;; Calculate attack force
        attack-force (* (attacker-stats :attack) (/ attacker-health attacker-maxhealth))
        ;; Ask user for defender type
        defender-type (do (println "Defender: unit-type?") (read-line))
        ;; Get defender data from unit-map
        defender (unit-map (keyword defender-type))
        ;; Ask user for defender health
        defender-health (do (println "health?") (Integer/parseInt (read-line)))
        ;; Get unit base max health from defender data; ask user if veteran and if so, add 5
        defender-maxhealth (+ (defender :max-health) (if (defender :can-be-vet?) (do (println "veteran? y/n") (if (= (read-line) "y") 5 0)) 0))
        ;; Ask user if unit is defended; if so, ask if it's fortified
        defense-bonus (do (println "defended? y/n") (if (= (read-line) "y") (if (defender :fortify?) (do (println "fortified? y/n") (if (= (read-line) "y") 4 1.5)) 1.5) 1))
        ;; Ask user if unit is embarked (if it's not fortified); if so, ask for ship type (cloaks can only be dinghys, and daggers can only be pirates), and get data from ship-map; otherwise, keep using unit-map data
        defender-stats (if (not= defense-bonus 4) (do (println "embarked? y/n") (if (= (read-line) "y") (cond (= defender-type "cloak") (ship-map :dinghy) (= defender-type "dagger") (ship-map :pirate) :else (do (println "ship-type?") (ship-map (keyword (read-line))))) defender)) defender)
        ;; Calculate defense force
        defense-force (* (defender-stats :defense) (/ defender-health defender-maxhealth) defense-bonus)
        ;; Ask user for distance between attacker and defender; loop until user enters a number within attacker range
        distance (loop [] (println "distance?") (let [result (Integer/parseInt (read-line))] (if (> result (attacker-stats :range)) (do (println "distance can't be greater than attacker range (" (attacker-stats :range) ")") (recur)) result)))
        ;; Calculate total damage
        total-damage (+ attack-force defense-force)
        ;; Calculate attack result
        attack-result (Math/round (* (/ attack-force total-damage) (attacker-stats :attack) 4.5))
        ;; Calculate defense result
        defense-result (Math/round (* (/ defense-force total-damage) (defender-stats :attack) 4.5))
        ;; Calculate defender new health
        defender-newhealth (if (< (- defender-health attack-result) 0) 0 (- defender-health attack-result))
        ;; Calculate attacker new health, if the defender was not killed, and if attacker is in defender's range
        attacker-newhealth (if (and (<= distance (defender-stats :range)) (> defender-newhealth 0)) (if (< (- attacker-health defense-result) 0) 0 (- attacker-health defense-result)) attacker-health)]
    ;; Print results
    (println "Attacker:" (attacker-stats :type) "with" attacker-health "out of" attacker-maxhealth "health\nDefender:" (defender-stats :type) "with" defender-health "out of" defender-maxhealth "health\nRange:" distance "\nResults:\nAttacker:" attacker-newhealth "out of" attacker-maxhealth "health" (if (= 0 attacker-newhealth) "(dead)" "") "\nDefender:" defender-newhealth "out of" defender-maxhealth "health" (if (= 0 defender-newhealth) "(dead)" ""))))