(defproject sandal "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-commons/fs "1.6.307"]
                 [clojure-lanterna "0.9.7"]
                 [aprint "0.1.3"]
                 [table "0.5.0"]]
  :source-paths ["."]
  :main sandal.core
  :repl-options {:init-ns sandal.core})
