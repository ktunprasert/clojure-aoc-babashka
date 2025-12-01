(ns day01
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(defn parse [fname] (with-open [r (io/reader fname)] (doall (line-seq r))))

(defn -main [& args]
  (->> args
       first
       parse
       (map #(->> (str/split % #"\s{3}")
                  (map Integer/parseInt)))
       (reduce (fn [[l r] line]
                 ; (pprint [l r line])
                 [(conj l (first line)) (conj r (second line))]) [[] []])
       ((fn [[v freq]] [v (frequencies freq)]))
       ((fn [[v freq]] (map (fn [n] (* n (get freq n 0))) v)))
       (apply +)
       pprint
       ))

(apply -main *command-line-args*)
