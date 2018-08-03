(ns clojure-project.core
  (:require [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]
            [clojure.string :as strs]))

(def url "https://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=1H0008UPE0000&processo.foro=53&uuidCaptcha=sajcaptcha_2739699031fa49a79ba4f010644d55f3")

(defn get-dom
  [url]
  (html/html-snippet
    (:body @(http/get url {:insecure? true}))))

(defn get-movs-info
  "Get the tbody the containing the content of all movs"
  [content]
  (html/select content [:tbody#tabelaTodasMovimentacoes])
  )

(defn get-trs
  "Find all trs elements from the mov"
  [movs-info]
  (html/select movs-info [:tr])
  )


(defn split-movs [tr-text]
  "Find the mov date"
  (first (re-seq #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}" tr-text))
  )

(defn clean [texto]
  "Clean the text of a mov"
  (strs/replace texto #"\t|\n" ""))

(defn -main []

  (let [movs-content (get-movs-info (get-dom url))
        trs-content (get-trs movs-content)
        text-content (vec (map html/text trs-content))
        cleaned-text (vec  (map clean text-content))
        ]
    (println (map split-movs cleaned-text ))

    )


  )




;(def url "https://www.oantagonista.com/")

