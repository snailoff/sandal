(ns sandal.core
  (:require [lanterna.screen :as s]
            [clojure.string :as str]
            [me.raynes.fs :as fs]
            [aprint.core :refer [aprint]]))

(def sandal-state
  (atom {:work-id        "TimeDefenders"
         :package-prefix "games.timedefenders" ;; package-prefix.project-id
         :base-path      "/Users/snailoff/workspace/clojure/sandal_workspace"}))

;(defn workspace-info []
;  (fs/exists? (@sandal-state :base-path))
;  (doseq [[k v] @sandal-state]
;    (clojure.pprint/pprint )))
(defn- path-workspace []
  (format "%s/%s"
          (@sandal-state :base-path)
          (@sandal-state :work-id)))

(defn- path-project [project-name]
  (format "%s/%s"
          (path-workspace)
          project-name))

(defn- path-package [project-name target]
  (format "%s/src/%s/java/%s/%s"
          (path-project project-name)
          target
          (str/replace (@sandal-state :package-prefix) #"\." "/")
          project-name))

(defn make-workspace []
  (let [path (path-workspace)]
    (if-not (fs/exists? path)
      (fs/mkdir path))))

(defn make-project [project-name]
  (let [target-main (path-package project-name "main")
        target-test (path-package project-name "test")]
    (if-not (fs/exists? target-main)
      (fs/mkdirs target-main))
    (if-not (fs/exists? target-test)
      (fs/mkdirs target-test))))


(defn -main
  [& args]
  (println "Hello world!")
  (aprint @sandal-state))

