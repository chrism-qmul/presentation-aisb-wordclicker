(ns presentation.core
  (:require [reagent.core :as reagent :refer [atom]]
            [presentation.reveal :as reveal]))

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn video-slide [video-url]
  [:section {:data-background-video video-url
             :data-background-video-loop "true"
             :data-background-size "cover"}])

(defn intro []
  [:section
    [:ul
     [:li "Broad research topic of GWAPs for natural language resourcing"]
     [:li "This involves text annotation, often complex text annotation"]
     [:li "text itself can, there's plenty of text orientated games"]
     [:li "Annotation isn't a mechanic that really fits into games"]
     [:li "asking someone to label noun phrase after noun phrase for example, or which terms are coreferrent, doesn't"]]])

(defn motivation []
  [:section
    [:p "So I first looked at that problem, rather than thinking about gathering annotations
Simply, can I have people perform the task of text annotation in a game"]
    [:p "Considering the design constraints I realised:"]
    [:ol
      [:li "It can't cost a lot to make (otherwise I'd be better off crowdsourcing the annotations)"]
      [:li "It has to be infinite - one cannot afford to make lots of in game resources in line with the annotation requirements (see 1!)"]]])

(defn falling-cakes [n]
  (let [cake-images (repeat n [:div.cake>img {:src "cake.svg"}])]
    (into [:div#bg-container>div.falling-background] cake-images)))

(defn feedback []
  [:section
   [:section
    [:h1 "Feedback"]
    [:p "blah feedback"]]
   [video-slide "video/feedback-compressed.mp4"]])

(defn precision []
  [:section
   [:section
    [:h1 "Precision"]
    [:p "blah"]]
   [video-slide "video/streak-compressed.mp4"]])

(defn recall []
  [:section
   [:section
    [:h1 "Recall"]]
   [video-slide "video/requireall-compressed.mp4"]])

(defn new-annotations []
  [:section
   [:section
    [:h1 "New Annotations"]
    [:p "new anno"]]
   [video-slide "video/mystery-compressed.mp4"]])

(defn conclusion []
  [:section
   [:h1 "Future work"]
   [:p "Further work needs to be done to test the true impact of these"]
   [:p "Not only on accuracy, but enjoyability"]])

(defn home []
  [:div
   [falling-cakes 5]
   [reveal/presentation {:controls false}
    [intro]
    [motivation]
    [feedback]
    [precision]
    [recall]
    [new-annotations]
    [conclusion]
    ]])

(defn start []
  (reagent/render-component [home]
                            (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
