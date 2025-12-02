(ns y2025.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn parse
  [fname]
  (-> (io/file fname)
      slurp
      str/trim
      (str/split #",")
      (->> (map #(str/split % #"-"))
           (map #(map Integer/parseInt %)))))

; [filename]
  ; (with-open [r (io/reader filename)] (doall (line-seq r))))

; find all numbers in range where
; prefix of eN/2 equals to suffix of eN/2
; skip where N is base of odd

(defn -main
  [& args]
  (->> (first args)
       parse
       pprint))

(apply -main *command-line-args*)
