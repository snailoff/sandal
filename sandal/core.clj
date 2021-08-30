(ns sandal.core
  (:require [lanterna.screen :as s]
            [clojure.string :as str]
            [me.raynes.fs :as fs]
            [aprint.core :refer [aprint]]))

(def sandal-state
  (atom {:work-id        "TimeDefenders"
         :package-prefix "games.timedefenders" ;; package-prefix.project-id
         :base-path      "c:/TimeDefenders"}))

;(defn workspace-info []
;  (fs/exists? (@sandal-state :base-path))
;  (doseq [[k v] @sandal-state]
;    (clojure.pprint/pprint )))
(defn workspace-make []
  (if-not (fs/exists? (@sandal-state :base-path))
    (fs/mkdir (@sandal-state :base-path))))

(defn- path-project [target]
  (format "%s/%s/src/%s/java"
          (@sandal-state :base-path)
          (@sandal-state :)
          target))

(defn- path-project-package [project-name target]
  (format "%s/%s"
          (path-project project-name target)
          (str/replace (@sandal-state :package-prefix) #"\." "/")))

(defn project-make [name]
  (let [src-main (path-project-package )
        prefix (str/replace (@sandal-state :package-prefix) #"\." "/")
        src-main (str root "/" name "/src/main/java/" prefix)
        src-test (str root "/" name "/src/test/java/" prefix)]
    (fs/mkdirs src-main)
    (fs/mkdirs src-test)))


(defn -main
  [& args]
  (println "Hello world!")
  (aprint @sandal-state)
  )
