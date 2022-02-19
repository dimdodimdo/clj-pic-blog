(ns cljpicblog.admin)

(def admin-login (or (System/getenv "CLJBLOG_ADMIN_LOGIN")   "admin"))
(def admin-password (or (System/getenv "CLJBLOG_ADMIN_PASSWORD")  "admin"))

(defn check-login [login password]
  (and (= login admin-login)
       (= password admin-password))
  )
