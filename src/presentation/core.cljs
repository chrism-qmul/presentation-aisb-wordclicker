(ns presentation.core
  (:require [reagent.core :as reagent :refer [atom]]
            [presentation.reveal :as reveal]))

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn video-slide [video-url & {:keys [notes]}]
  [:section {:data-background-video video-url
             :data-background-video-loop "true"
             :data-background-size "contain"}
    [:aside.notes notes]]
  )

(defn title []
  [:section
   [:h1.fancyfont "WordClicker"]
   [:h2.fancyfont "Tuning our approach for Language Resourcing"]])

(defn intro []
  [:section
   [:h1.fancyfont "Background"]
    [:ul
     [:li "Initially the problem we set out to solve was how we could use games for language resourcing"]
     [:li "This involves text annotation, often complex text annotations"]
     [:li "Annotation however isn't a mechanic that really fits into games"]
     [:li "This is a very challenging design space"]]])

(defn motivation []
  [:section
   [:h1.fancyfont "Background"]
    [:ul 
     [:li "We split this problem in two:"]
     [:ol 
      [:li "How can we get a text annotation mechanic in a game?"]
      [:li "How can we use a game to gather text annotations?"]]]
    [:aside.notes "the first question revolves around challenges like attracting and retaining players in a game, and the design challenges involved in text annotation as a mechanic; the second question is around how to motivate a suitable level of accuracy from player interactions without negatively impacting enjoyability"]])

(defn falling-cakes [n]
  (let [cake-images (repeat n [:div.cake>img {:src "cake.svg"}])]
    (into [:div#bg-container>div.falling-background] cake-images)))

(defn feedback []
  [:section
   [:section
    [:h1.fancyfont "Feedback"]
    [:p "We first needed a way to offer ongoing feedback..."]
    [:ul
     [:li "We didn't want to break immersion by discussing accuracy"]
     [:li "We wanted an overall picture showing progress as they learn"]]]
   [video-slide "video/feedback-compressed.mp4" :notes "Here the player can view a company report, which shows accuracy as company reputation"]])

(defn precision []
  [:section
   [:section
    [:h1.fancyfont "Precision"]
    [:ul
     [:li "Incremental games don't usualy penalise users"]
     [:li "We introduced an additional perk instead"]
     [:li "A \"streak\" mechanic is introduced to try to softly encourage more consistent and considered interaction"]]]
   [video-slide "video/streak-compressed.mp4" :notes "Here we can see when the player consistently marks correctly, they eventually are rewarded with double points.  If they make a mistake, this perk is removed"]])

(defn recall []
  [:section
   [:section
    [:h1.fancyfont "Recall"]
    [:ul
     [:li "Currently, when the user plays the game they could favour a single familiar part-of-speech"]
     [:li "We wanted players to explore a broad range of labels"]
     [:li "To achieve this we require the player has all parts of speech present before they score points"]]]
   [video-slide "video/requireall-compressed.mp4" :notes "Here we can see whilst the ingredients jars are filled by marking correct labels, they don't start producing rewards unless all ingredients are available, encouraging the user to explore all the categories"]])

(defn new-annotations []
  [:section
   [:section
    [:h1.fancyfont "New Annotations"]
    [:ul
     [:li "We didn't have a way for users to add annotations for which we didn't know the answer"]
     [:li "This modification allows for mystery rounds - where we let the player annotate an item but score points later"]]]
   [video-slide "video/mystery-compressed.mp4" :notes "Perhaps most importantly, we need to gather annotations for items we do not know the answer to, and therefore cannot reward the user.  We introduce mystery rounds periodically in line with the users performance, and reward them with points if their answer agreed with other users"]])

(defn original-game []
  [:section
   [:section
   [:h1.fancyfont "WordClicker"]
   [:ul 
     [:li "This brings us to the original version of the game - a clicker game (wordclicker.com)"]
     [:ul
      [:li [:b "Design Challenges: "] "Time/Cost; Repetitive/Infinite; Ludic Efficiency; Casual/Inclusive"]
      [:li>i>small "Madge, C., Bartle, R., Chamberlain, J., Kruschwitz, U. and Poesio, M., 2019, October. Incremental game mechanics applied to text annotation. In Proceedings of the Annual Symposium on Computer-Human Interaction in Play (pp. 545-558)."]]]
   [:aside.notes "There were multiple challenges to overcome, we identified these as firstly, time and cost.  We couldn't make a game that competes with big games, else it would be cheaper to adopt other methods.  We needed something that had a high degree of ludic efficiency.  In other words, we couldn't add entertainment value by problematising the interface, out system needed to stay easy to interact with.  The game needed to be inclusive of all kinds of players.  Not being a keen gamer shouldn't stop players from contributing.  The existing sub-genre Clicker games fit well here"]]
   [video-slide "video/original.mp4" :notes "Here is the original version of wordclicker.  It starts with a homepage and some instructions.  When the user clicks play, this is followed by a tutorial.  In the typical clicker game, the player experiences a loop in which the core mechanic, clicking gives reward, which can be used to purchase upgrades, that then accelerates future reward.  Here we replace the core mechanic of clicking, with clicking on annotations.  As there are multiple lexical classes, correct annotations feed into categorised ingredients jars, which then produce cakes and money"]])

(defn conclusion []
  [:section
   [:section
   [:h1.fancyfont "Future work"]
   [:p "Further work needs to be done to test the true impact of these mechanics"]
   [:ul
    [:li  "We are not only looking at accuracy, but enjoyability"]
    [:li  "We need to examine the effect between mechanics - does increasing precision decrease recall?"]
    [:li  "We need to look at how players behave in the mystery rounds"]]]
   [:section
    [:h1.fancyfont "Questions"]
    [:p.center "You can play the prototype version over at" [:br] "dev.wordclicker.com"]]])

(defn home []
  [:div
   [falling-cakes 5]
   [reveal/presentation {:controls false, :plugins [reveal/notes]}
    [title]
    [intro]
    [motivation]
    [original-game]
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
