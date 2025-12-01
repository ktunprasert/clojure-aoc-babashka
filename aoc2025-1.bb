#!/usr/bin/env bb

(ns aoc2025-1
  (:require
    [babashka.cli :as cli]
    [clojure.java.io :as io]))

;; ──────────────────────────────────────────────────────────────
;; Your puzzle logic (unchanged)
;; ──────────────────────────────────────────────────────────────
(defn parse [filename]
  (with-open [r (io/reader filename)]
    (doall (line-seq r))))

(defn step-state [[pos zero-hits pass-hits] instr]
  (let [dir   (first instr)
        mag   (Integer/parseInt (subs instr 1))
        delta (if (= dir \L) (- mag) mag)
        step  (if (pos? delta) 1 -1)
        steps (range 1 (inc (Math/abs delta)))
        positions (map #(mod (+ pos (* step %)) 100) steps)
        hits      (count (filter zero? positions))
        new-pos   (mod (+ pos delta) 100)]
    [new-pos
     (+ zero-hits (if (zero? new-pos) 1 0))
     (+ pass-hits hits)]))

;; ──────────────────────────────────────────────────────────────
;; CLI entry point (unchanged)
;; ──────────────────────────────────────────────────────────────
(defn solve-aoc [{:keys [file]}]
  (if-not file
    (do (println "Usage: bb aoc2025-1.bb <input-file> (or ./aoc2025-1.bb <input-file>)")
        (System/exit 1)))
  (let [instructions (parse file)
        [pos landings passes] (reduce step-state [50 0 0] instructions)]
    (println "Final position :" pos)
    (println "Landings on 0  :" landings)
    (println "Passes over 0  :" passes)))

;; ──────────────────────────────────────────────────────────────
;; CLI definition (unchanged)
;; ──────────────────────────────────────────────────────────────
(def cli-opts
  {:spec   {:file {:coerce :string
                   :desc   "Input file"}}
   :exec-fn solve-aoc})

;; ──────────────────────────────────────────────────────────────
;; Correct invocation: dispatch with opts + sliced command-line-args
;; (slices off "./aoc2025-1.bb" so positional <file> works cleanly)
;; ──────────────────────────────────────────────────────────────
(cli/dispatch cli-opts (drop 1 *command-line-args*))
