(ns y2025.day02
  (:require
   [clojure.java.io :as io]
   [clojure.math :as math]
   [clojure.pprint :refer [pprint]]
   [clojure.string :as str])
  (:gen-class))

(defn parse
  [fname]
  (-> (io/file fname)
      slurp
      str/trim
      (str/split #",")
      (->> (map #(str/split % #"-"))
           (map #(map Integer/parseInt %)))))

(defn log10 [num]
  (loop [x num e 0]
    ; (pprint [x e])
    (cond
      (> x 1) (recur (/ x 10) (inc e))
      (= x 1) (- e 1)
      :else e)))

; find all numbers in range where
; prefix of eN/2 equals to suffix of eN/2
; skip where N is base of odd
(defn solve [range-pairs]
  (->> range-pairs
       (map (fn [[r1 r2]] (range r1 (inc r2))))
       flatten
       (map #(vector % (log10 %)))
       (filter #(-> (second %) even?))
       (map (fn [[n e]]
              (let [middle (math/pow 10 (/ e 2))]
                [(quot n middle) (rem n middle) n])))
       (filter (fn [[l r]] (= l r)))
       (map last)
       (reduce +)))

(defn -main
  [& args]
  (->> (first args)
       parse
       solve
       pprint))

(apply -main *command-line-args*)
