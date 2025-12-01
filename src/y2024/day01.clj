(ns day01
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(defn parse [fname]
  (->>  (with-open [r (io/reader fname)] (doall (line-seq r)))
        (map #(->> (str/split % #"\s{3}")
                   (map Integer/parseInt)))))

(defn solve [vec]
  (let [unzipped (apply map vector vec)
        sorted (map sort unzipped)
        zipped (apply map vector sorted)
        v (first unzipped)
        freq (frequencies (second unzipped))]
    (-> [(->> (map (fn [[a b]] (abs (- a b))) zipped) (apply +))
         (->> (map (fn [n] (* n (get freq n 0))) v) (apply +))]
        pprint)))

(defn -main [& args]
  (->> args
       first
       parse
       solve))

(apply -main *command-line-args*)
