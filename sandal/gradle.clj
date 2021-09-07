(ns sandal.gradle
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [sandal.config :as scf]
            [me.raynes.fs :as fs]))


(def resource-gradle-settings "resources/templates/gradle/settings.gradle")
(def resource-gradle-build-root "resources/templates/gradle/build_root.gradle")
(def resource-gradle-build-common "resources/templates/gradle/build_common.gradle")
(def resource-gradle-build-project "resources/templates/gradle/build_project.gradle")

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




(defn- copy-template-gradle-settings [target]
  (fs/copy resource-gradle-settings target))

(defn- copy-template-gradle-build-root [target]
  (fs/copy resource-gradle-build-root target))

(defn- copy-template-gradle-build-common [target]
  (fs/copy resource-gradle-build-common target))

(defn- copy-template-gradle-build-project [target]
  (fs/copy resource-gradle-build-project target))


(defn launch-gradle-settings [target]
  (if-let [target (copy-template-gradle-settings target)]
    (replace-template-gradle-settings target)))

(defn launch-gradle-build-root [target]
  (if-let [target (copy-template-gradle-build-root target)]
    (replace-template-gradle-build-root target)))

(defn launch-gradle-build-common [target]
  (copy-template-gradle-build-common target))

(defn launch-gradle-build-project [target]
  (copy-template-gradle-build-project target))












