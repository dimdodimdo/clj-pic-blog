(ns cljpicblog.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as resp]
            [ring.middleware.session :as session]
            [cljpicblog.db :as db]
            [cljpicblog.pages :as p]
            [cljpicblog.admin :as a]))



(defroutes app-routes
           (GET "/" [] (p/index (db/get-all)))
           (GET "/post/:pic-id" [pic-id] (p/pic (db/get-by-id pic-id)))
          (POST "/post/:pic-id" [pic-id author title body pic]
             (do (db/update pic-id author title body pic)
                 (resp/redirect (str "/post/" pic-id))))

           (GET "/admin/login" [:as {session :session}]
             (if (:admin session)
               (resp/redirect "/")
               (p/admin-login))
             )
           (POST "/admin/login" [login password]
             (if (a/check-login login password)
               (-> (resp/redirect "/")
                   (assoc-in [:session :admin] true))
               (p/admin-login)))

           (GET "/admin/logout" []
             (-> (resp/redirect "/")
             (assoc-in [:session :admin] false)))

           (route/not-found "Not Found")
           )

(defroutes admin-routes
           (GET "/post/new" [] (p/edit-post nil))
           (POST "/post" [author title body pic]
             (do (db/create author title body pic)
                 (resp/redirect "/")))

           (GET "/post/:pic-id/edit" [pic-id] (p/edit-post (db/get-by-id pic-id)))
           )
(defn wrap-admin-only [handler]
  (fn [request]
    (if (-> request :session :admin)
      (handler request)
      (resp/redirect "/admin/login"))))

  (def app
       (-> (routes (wrap-routes admin-routes wrap-admin-only)
                   app-routes)

           (wrap-defaults site-defaults)
           session/wrap-session)
       )

