(ns sandal.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [sandal.gradle :as sgr]
            [sandal.config :as scf]
            [me.raynes.fs :as fs]
            [aprint.core :refer [aprint]]))

(defn- path-workspace []
  (format "%s/%s"
          (scf/config :base-path)
          (scf/config :workspace-id)))

(defn- path-project [project-name]
  (format "%s/%s"
          (path-workspace)
          project-name))

(defn- path-package [project-name target]
  (format "%s/src/%s/java/%s/%s"
          (path-project project-name)
          target
          (str/replace (scf/config :package-prefix) #"\." "/")
          (str/replace project-name #"-" "/")))


(defn create-workspace []
  (let [path (path-workspace)
        target-settings (str (path-workspace) "/settings.gradle")
        target-build (str (path-workspace) "/build.gradle")]
    (if-not (fs/exists? path)
      (fs/mkdir path))

    (sgr/launch-gradle-settings target-settings)
    (sgr/launch-gradle-build-root target-build)))


(defn create-project [project-name]
  (let [target-main (path-package project-name "main")
        target-test (path-package project-name "test")]
    (if-not (fs/exists? target-main)
      (fs/mkdirs target-main))
    (if-not (fs/exists? target-test)
      (fs/mkdirs target-test)))

  (let [target-build (str (path-project project-name) "/build.gradle")]
    (cond
      (= project-name "common-data") (sgr/launch-gradle-build-common target-build)
      (= project-name "common-module") (sgr/launch-gradle-build-common target-build)
      :else (sgr/launch-gradle-build-project target-build))))


;; module create use
(defn create-module [])

;; function create user userInfo
;; => userInfoControlelr, userInfoService
(defn create-function [])

(defn create-model-from-db [table-name])

(defn -main
  [& _]
  (aprint scf/config)
  (create-workspace)
  (create-project "common-data")
  (create-project "backend")
  (println "done?"))


