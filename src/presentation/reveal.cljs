(ns presentation.reveal
  (:require [reagent.core :as reagent :refer [atom]]
            ["reveal.js" :as Reveal]
            ["reveal.js/plugin/notes/notes.esm.js" :as Notes]))

(defonce reveal-state (atom nil))

(def notes Notes/default)

(defn presentation [options & slides]
  (reagent/create-class 
    {:component-did-mount
     (fn [component]
       (let [update-events ["slidechanged" "overviewshown" "overviewhidden"]
             element (reagent/dom-node component)
             reveal (Reveal. element (clj->js options))
             save-state #(reset! reveal-state (.getState reveal))
             restore-state #(when (some? @reveal-state) (.setState reveal @reveal-state))]
         (doseq [event update-events] (.on reveal event save-state))
         (.. reveal (initialize) (then restore-state))))
     :reagent-render
     (fn []
       (into [:div.reveal>div.slides] slides))}))

