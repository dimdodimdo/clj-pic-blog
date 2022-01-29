(ns cljpicblog.pages
  (:require [hiccup.page :refer [html5]]))

(defn base-page [& body]
  (html5
    [:head [:title "CljPicBlog"]]
    [:body [:h1 "CljPicBlog"]
     body]
    )
  )

(defn index [posts]
  (base-page
    (for [p posts]
      [:h2 [:a {:href (str "/articles/" (:_id p))} (:title p)]])
    )
  )


(defn pic [a]
  (base-page
    [:small (:created a)]
    [:h1 (:title a)]
    [:p (:body a)])
  )

