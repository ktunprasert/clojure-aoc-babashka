(ns day01
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

; (defn parse [fname]
;   (->>  (with-open [r (io/reader fname)] (doall (line-seq r)))
;         (map #(->> (str/split % #"\s{3}")
;                    (map Integer/parseInt)))))

(defn parse [fname]
  (-> (io/file fname)
      slurp
      str/split-lines
      (->> (map (fn [line] (->> (str/split line #"\s{3}") (mapv parse-long))))
           vec)))

(defn solve [vec]
  (let [[v1 v2] (apply map vector vec)
        sorted-pairs (map sort [v1 v2])
        zipped (apply map vector sorted-pairs)
        freq (frequencies v2)

        sum-abs-dist (reduce + (map (fn [[a b]] (abs (- a b))) zipped))
        sum-freq-prod (reduce + (map (fn [n] (* n (get freq n 0))) v1))]
    [sum-abs-dist sum-freq-prod]))

(defn -main [& args]
  (->> args
       first
       parse
       solve
       pprint))

(apply -main *command-line-args*)
