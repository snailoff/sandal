(ns sandal.gradle
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [me.raynes.fs :as fs]
            [sandal.config :as scf]))

(defn- replace-template-gradle-settings [target-file]
  (if-let [content (slurp target-file)]
    (let [new-content (-> content
                          (str/replace #"__root-project-name__" (scf/config :workspace-id)))]
      (with-open [wr (io/writer target-file)]
        (.write wr new-content)))))

(defn- replace-template-gradle-build-root [target-file
                                           & {:keys [spring-boot-version
                                                     swagger-version
                                                     old-swagger2-version
                                                     querydsl-version
                                                     source-compatibility]
                                              :or {spring-boot-version "2.5.3"
                                                   swagger-version "2.9.2"
                                                   old-swagger2-version "1.5.21"
                                                   querydsl-version "4.4.0"
                                                   source-compatibility "11"}}]
  (if-let [content (slurp target-file)]
    (let [new-content (-> content
                          (str/replace #"__spring-boot-version__" spring-boot-version)
                          (str/replace #"__swagger-version__" swagger-version)
                          (str/replace #"__old-swagger2-version__" old-swagger2-version)
                          (str/replace #"__querydsl-version__" querydsl-version)
                          (str/replace #"__group__" (scf/config :package-prefix))
                          (str/replace #"__source-compatibility__" source-compatibility))]
      (with-open [wr (io/writer target-file)]
        (.write wr new-content)))))


(defn launch-gradle-settings [to]
  (let [from "resources/templates/gradle/settings.txt"]
    (if-let [target (fs/copy from to)]
      (replace-template-gradle-settings target))))

(defn launch-gradle-build-root [to]
  (let [from "resources/templates/gradle/build_root.txt"]
    (if-let [target (fs/copy from to)]
      (replace-template-gradle-build-root target))))

(defn launch-gradle-build-common [to]
  (let [from "resources/templates/gradle/build_common.txt"]
    (fs/copy from to)))

(defn launch-gradle-build-project [to]
  (let [from "resources/templates/gradle/build_project.txt"]
    (fs/copy from to)))













