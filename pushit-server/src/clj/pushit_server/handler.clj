(ns pushit-server.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response content-type]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app-routes
  (GET "/" [] (content-type (resource-response "index.html" {:root "public"}) "text/html"))
  (GET "/widgets" [] (response [{:name "Widget 1"} {:name "Widget 2"}]))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrap-defaults api-defaults)))
