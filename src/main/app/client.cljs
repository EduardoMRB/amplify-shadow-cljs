(ns app.client
  (:require
   ["aws-amplify" :refer [Amplify Auth Analytics]]
   [promesa.core :as p]))

(defn init []
  (js/console.log "init" Amplify)
  (.configure Amplify
    (clj->js {:identityPoolId ""
              :region ""
              :identityPoolRegion ""
              :userPoolId ""
              :userPoolWebClientId ""
              :signUpVerificationMethod "code"
              :Auth {:identityPoolId ""
                     :region ""}
              :Analytics {:disabled false
                          ;; Automatic updateEndpoint call fails.
                          :autoSessionRecord true
                          :AWSPinpoint {:appId ""
                                        :region ""}}})))

(defn refresh []
  (js/console.log "refreshing"))

(comment
  (-> (.currentAuthenticatedUser Auth)
    (p/then (fn [u]
              (js/console.log :u u))))

  (-> (.signIn Auth "user" "pass")
    (p/then (fn [u]
              (js/console.log :user u))))

  (.signOut Auth)

  (js/console.log Analytics)

  ;; Does not send any requests.
  (-> (.record Analytics #js {:name "signUp"
                              :immediate true})
    (p/then (fn [data] (js/console.log :data data)))
    (p/catch (fn [err] (js/console.error err)))))
