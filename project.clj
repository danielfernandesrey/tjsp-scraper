(defproject clojure_project "0.1.0-SNAPSHOT"
  :main clojure-project.core
  :aot [clojure-project.core]
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.9.1"]
                 [enlive "1.1.6"]
                 [http-kit "2.1.18"]])
