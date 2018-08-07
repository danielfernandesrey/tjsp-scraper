(ns tjsp-scraper.scraper_test
  (:require [clojure.test :refer :all]
            [tjsp-scraper.scraper :refer :all]))

(deftest scraper-test

  (testing "get-process-params"
    (let [numeracao_unica "11277690920168260100"
          params (get-process-params numeracao_unica)
          query-params (get params :query-params)
          numero-digito-ano-unificado (get query-params :numeroDigitoAnoUnificado)
          foro-numero-unificado (get query-params :forumNumeroUnificado)
          ]
      (is (instance? clojure.lang.PersistentArrayMap params))
      (is (= numero-digito-ano-unificado (subs numeracao_unica 0 13)))
      (is (= foro-numero-unificado (subs numeracao_unica 16)))
      )
    )
  )

