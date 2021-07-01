(defproject Turtle "0.1.0-SNAPSHOT"
  :description "Turtle Graphics Processor"
  :main turtle-graphics.core
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [quil "2.5.0"]
                 [org.clojure/core.async "1.3.610"]
                 [org.clojure/tools.namespace "1.1.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
