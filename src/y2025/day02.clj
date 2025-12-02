(ns y2025.day02
  (:require
   [clojure.java.io :as io]
   [clojure.math :as math]
   [clojure.pprint :refer [pprint]]
   [clojure.string :as str])
  (:gen-class))

; (defn parse
;   [fname]
;   (-> fname
;       slurp
;       str/trim-newline
;       (str/split #",")
;       ((partial map (comp (partial map Long/parseUnsignedLong)
;                           #(str/split % #"-"))))))

(defn parse
  [fname]
  (->> fname
       slurp
       (re-seq #"(\d+)-(\d+)")
       (map (fn [[_ start end]] (map Long/parseUnsignedLong [start end])))))

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

(def re-twice #"^(\d{1,5})\1$")
(def re-at-least-twice #"^(\d{1,5})\1+$")

(defn solve-re [range-pairs]
  (let [solve (fn [regex]
                (->> range-pairs
                     (map (fn [[r1 r2]] (range r1 (inc r2))))
                     (mapcat (partial filter (fn [n-str] (re-matches regex (str n-str)))))
                     (reduce +)))]
    [(solve re-twice) (solve re-at-least-twice)]))

(defn -main
  [& args]
  (time (->> (first args)
             parse
             solve-re
             pprint)))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
