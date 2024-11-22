(ns clojure-spellchecker.core
  (:require [clojure.string :as str])
  (:import (org.apache.commons.lang3 StringUtils))
  (:gen-class))

(def words 
  (set (map str/trim (str/split-lines (slurp "./resources/wordsEn.txt")))))

(defn correct?
  [word]
  (contains? words word))

(defn distance
  [a b]
  (StringUtils/getLevenshteinDistance a b))

(def distance-to-spelling (partial distance "spelling"))

(defn min-distance
  [word]
  (apply min-key (partial distance word) words))

(defn -main
  [& args]
  (let [word (first args)]
    (if (correct? word)
      (println "correct")
      (println "did you mean" (min-distance word) "?"))))
