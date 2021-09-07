(ns sandal.application
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [me.raynes.fs :as fs]
            [sandal.config :as s-cf]))


(defn- replace-template-application [target-file package-project project-capital]
  (if-let [content (slurp target-file)]
    (let [new-content (-> content
                          (str/replace #"__package-project__" package-project)
                          (str/replace #"__project-capital__" project-capital))]
      (with-open [wr (io/writer target-file)]
        (.write wr new-content)))))

(defn launch-application-class [to project-name]
  (let [from "resources/templates/application/Application.txt"
        package-project (str (s-cf/config :package-prefix) "." project-name)
        project-capital (str/capitalize project-name)]
    (if-let [target (fs/copy from to)]
      (replace-template-application target package-project project-capital))))
