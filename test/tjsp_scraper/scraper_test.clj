(ns tjsp-scraper.scraper_test
  (:require [clojure.test :refer :all]
            [tjsp-scraper.scraper :refer :all]
            [clojure.string :as strs]))

(deftest scraper-test

  (testing "get-process-params"
    (let [numeracao-unica "11277690920168260100"
          params (get-process-params numeracao-unica)
          query-params (get params :query-params)
          numero-digito-ano-unificado (get query-params :numeroDigitoAnoUnificado)
          foro-numero-unificado (get query-params :forumNumeroUnificado)
          ]
      (is (instance? clojure.lang.PersistentArrayMap params))
      (is (= numero-digito-ano-unificado (subs numeracao-unica 0 13)))
      (is (= foro-numero-unificado (subs numeracao-unica 16)))
      )
    )
  (testing "extract-process-info"
    (let [numeracao-unica "11277690920168260100"
          process-info (extract-process-info numeracao-unica)
          ]
      (is (contains? process-info :dados))
      (is (contains? process-info :partes))
      (is (contains? process-info :movimentacoes))
      (is (instance? clojure.lang.PersistentHashMap process-info))
      )
    )

  )

