(defproject project1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.1"]]
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler clj_project1.core/example-handler
         :init clj_project1.core/on-init
         :port 4001
         :destroy clj_project1.core/on-destroy})
