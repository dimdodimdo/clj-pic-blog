(ns cljpicblog.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as resp]
            [cljpicblog.db :as db]
            [cljpicblog.pages :as p]))

(defroutes app-routes
           (GET "/" [] (p/index (db/get-all)))
           (GET "/post/new" [] (p/edit-post nil))
           (POST "/post" [author title body pic]
             (do (db/create author title body pic)
                 (resp/redirect "/")))
           (GET "/post/:pic-id" [pic-id] (p/pic (db/get-by-id pic-id)))
           (GET "/post/:pic-id/edit" [pic-id] (p/edit-post (db/get-by-id pic-id)))
           (route/not-found "Not Found")
           )


(def app
  (wrap-defaults app-routes site-defaults))
