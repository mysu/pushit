(ns pushit-server.core
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http]))

(enable-console-print!)

; get widgets from server
(defn fetch-widgets
  [url]
  (let [c (chan)]
    (go (let [{widgets :body} (<! (http/get url))]
          (>! c (vec widgets))))
    c))

; widget component
(defn widget [{:keys [name]} owner opts]
  (om/component
    (dom/li nil name)))

; list of widgets
(defn widget-list [{:keys [widgets]}]
  (om/component
    (apply dom/ul nil
           (om/build-all widget widgets))))

(defn widget-box [app owner opts]
  (reify
    om/IWillMount
    (will-mount [_]
      (om/transact! app [:widgets] (fn [] []))
      (go (while true
            (let [widgets (<! (fetch-widgets (:url opts)))]
              (.log js/console (pr-str widgets))
              (om/update! app #(assoc % :widgets widgets)))
            (<! (timeout (:poll-interval opts))))))
    om/IRender
    (render [_]
      (dom/h1 nil "Widgets")
      (om/build widget-list app))))

; define an OM application
(defn om-app [app owner]
  (om/component
    (dom/div nil
             (om/build widget-box app
                       {:opts {:url "/widgets"
                               :poll-interval 2000}}))))

; initializes state
(def app-state
  (atom []))

; define the root of the application
(om/root app-state om-app (.getElementById js/document "content"))



