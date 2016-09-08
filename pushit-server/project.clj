(defproject pushit-server "0.1.0-SNAPSHOT"
  :description "PUSHIT: Server"
  :url "http://pushit.co/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.2.1"]
                 [org.clojure/tools.reader "0.10.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.async "0.2.385"]
                 [cljs-http "0.1.41"]
                 [om "0.7.3"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.4"]
            [lein-pdo "0.1.1"]]
  :ring {:handler pushit-server.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}}
  :source-paths ["src/clj"]
  :aliases {"up" ["pdo" "cljsbuild" "auto" "dev," "ring" "server-headless"]}
  :cljsbuild {:builds[{:id "dev"
                       :source-paths ["src/cljs"]
                       :compiler {:output-to "resources/public/js/app.js"
                                  :output-dir "resources/public/out"
                                  :optimizations :none
                                  :source-map true}}]})
