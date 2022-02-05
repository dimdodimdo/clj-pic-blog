(ns cljpicblog.pages
  (:require [hiccup.page :refer [html5]]
            [hiccup.form :as form]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            ))

(defn base-page [& body]
  (html5
    [:head [:title "CljPicBlog"]]

    [:body
     [:a {:href "/"} [:h1 "CljPicBlog"]
     [:a {:href "/post/new"} "New Post"]
     [:hr]]
     body]
    )
  )

(defn index [posts]
  (base-page
    (for [p posts]
      [:h2 [:a {:href (str "/post/" (:_id p))} (str  "Picture post: " ( :title p) )]]
      )
    )
  )


(defn pic [p]
  (base-page
    [:small (or (:created p)
                "no date specified") ]
    [:h1 (:title p)]
    [:p (:body p)]
    [:p (or (:pic p)
            "no pic added") ]
    )
  )

(defn edit-post [p]
  (base-page
    (form/form-to
      [:post (if p
               (str "/post/" (:_id p))
               "/post")]

      (form/label "author" "Author")
      (form/text-field "author" (:author p))

      (form/label "title" "Title")
      (form/text-field "title" (:title p))

      (form/label "body" "Body")
      (form/text-area "body" (:body p))

      (form/label "pic" "Picture")
      (form/text-area "pic" (:pic p))

      (anti-forgery-field)

      (form/submit-button "Save!")
      )))

