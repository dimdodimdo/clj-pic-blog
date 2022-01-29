(ns cljpicblog.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cljpicblog.db :as db]
            [cljpicblog.pages :as p]))

(defroutes app-routes
           (GET "/" [] (p/index (db/get-all)))
           (GET "/articles/:pic-id" [pic-id] (p/pic (db/get-by-id pic-id)))
           (route/not-found "Not Found")
           )


(def app
  (wrap-defaults app-routes site-defaults))
