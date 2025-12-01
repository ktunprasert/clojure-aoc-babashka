(ns core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn parse
  [filename]
  (with-open [r (io/reader filename)] (doall (line-seq r))))

(defn step-state
  [[pos zero-hits pass-hits] instr]
  (let [dir (first instr)
        n-str (subs instr 1)
        mag (Integer/parseInt n-str)
        delta (if (= dir \L) (- mag) mag)
        step (if (pos? delta) 1 -1)
        steps (range 1 (inc (Math/abs delta)))
        ;; every intermediate position we pass over
        positions (map #(mod (+ pos (* step %)) 100) steps)
        ;; how many times we *pass or land on* 0 during this move
        hits (count (filter zero? positions))
        new-pos (mod (+ pos delta) 100)]
    ;; If you want "landing exactly on 0" separate from "passing 0",
    ;; you can adjust these two accumulators differently.
    [new-pos (+ zero-hits (if (zero? new-pos) 1 0)) ; count landings on 0
     (+ pass-hits hits)]))                  ; count all passes over 0

(defn -main
  [& args]
  (->> (first args)
       parse
       (reduce step-state [50 0 0])
       println))

(apply -main *command-line-args*)
