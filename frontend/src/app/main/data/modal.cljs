;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; This Source Code Form is "Incompatible With Secondary Licenses", as
;; defined by the Mozilla Public License, v. 2.0.
;;
;; Copyright (c) 2020 UXBOX Labs SL

(ns app.main.data.modal
  (:refer-clojure :exclude [update])
  (:require
   [potok.core :as ptk]
   [app.main.store :as st]
   [app.common.uuid :as uuid]
   [cljs.core :as c]))

(defonce components (atom {}))

(defn show
  ([props]
   (show (uuid/next) (:type props) props))
  ([type props] (show (uuid/next) type props))
  ([id type props]
   (ptk/reify ::show-modal
     ptk/UpdateEvent
     (update [_ state]
       (assoc state ::modal {:id id
                            :type type
                            :props props
                            :allow-click-outside false})))))

(defn hide
  []
  (ptk/reify ::hide-modal
    ptk/UpdateEvent
    (update [_ state]
      (dissoc state ::modal))))

(defn update
  [options]
  (ptk/reify ::update-modal
    ptk/UpdateEvent
    (update [_ state]
      (c/update state ::modal merge options))))

(defn show!
  [type props]
  (st/emit! (show type props)))

(defn allow-click-outside!
  []
  (st/emit! (update {:allow-click-outside true})))

(defn disallow-click-outside!
  []
  (st/emit! (update {:allow-click-outside false})))

(defn hide!
  []
  (st/emit! (hide)))
