(ns clojure-project.core
  (:require [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]))

(def url "http://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=E200091IH0000&processo.foro=506&uuidCaptcha=sajcaptcha_9f4bf726470948bba03218716650a93a")

(defn get-dom
  [url]
  (html/html-snippet
    (:body @(http/get url {:insecure? true}))))

(defn get-movs-info
  [content]
  (html/select content [:tbody#tabelaTodasMovimentacoes])
  )

(defn -main []

  (get-movs-info (get-dom url))

  )




;(def url "https://www.oantagonista.com/")

