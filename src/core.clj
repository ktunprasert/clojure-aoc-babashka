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
        abs-delta (if (neg? delta) (- delta) delta)
        ;; Count zeros without building intermediate collection
        hits (transduce
               (comp (map #(mod (+ pos (* step %)) 100))
                     (filter zero?))
               (completing (fn [acc _] (inc acc)))
               0
               (range 1 (inc abs-delta)))
        new-pos (mod (+ pos delta) 100)]
    [new-pos
     (+ zero-hits (if (zero? new-pos) 1 0))
     (+ pass-hits hits)]))                  ; count all passes over 0

(defn -main
  [& args]
  (->> (first args)
       parse
       (reduce step-state [50 0 0])
       println))

; (apply -main *command-line-args*)
