(ns sandal.application
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [sandal.config :as scf]
            [me.raynes.fs :as fs]))

(defn- replace-template-application [target-file prefix]
  (if-let [content (slurp target-file)]
    (let [new-content (-> content
                          (str/replace #"__project-name__" prefix))]
      (with-open [wr (io/writer target-file)]
        (.write wr new-content)))))

(defn launch-application-class [to project-name]
  (let [from "resources/templates/application/Application.txt"
        prefix (str/capitalize project-name)]
    (if-let [target (fs/copy from to)]
      (replace-template-application target prefix))))
