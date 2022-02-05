(ns cljpicblog.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :refer [$set]])

  (:import
    [org.bson.types ObjectId]
    (java.util Date))
  )

(def db-connection-uri (or
                         (System/getenv "CLJBLOG_MONGO_URI")
                         "mongodb://127.0.0.1/cljpicblog"))
(def db (-> db-connection-uri
            mg/connect-via-uri
            :db))
(def posts-coll "posts")
(defn create [author title body pic]
  (mc/insert db posts-coll {
                            :author  author
                            :title   title
                            :body    body
                            :pic     pic
                            :created (new Date)
                            }))
(defn update [id title body pic]
  (mc/update-by-id db posts-coll
                   (ObjectId. id)
                   {$set
                    {:title title
                     :body body
                     :pic pic
                     }
                    })
  )
(defn delete [id]
  (mc/remove-by-id db posts-coll
                   (ObjectId. id)))
(defn get-all []
  (mc/find-maps db posts-coll))
(defn get-by-id [id]
  (mc/find-map-by-id db posts-coll (ObjectId. id)))


