(ns sandal.config)

(def config
  {:workspace-id   "TimeDefenders"
   :package-prefix "io.minimal"                             ;; package-prefix.project-id
   :base-path      "/Users/snailoff/workspace/clojure/sandal_workspace"
   :db             {:kind "mysql"
                    :host "localhost"
                    :port "3306"
                    :user "user"
                    :pass "password"
                    :db   "db"}})
