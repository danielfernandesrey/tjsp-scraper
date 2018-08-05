(ns clojure-project.core
  (:require [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]
            [clojure.string :as strs]))

(def url "http://esaj.tjsp.jus.br/cpopg/search.do")

(defn get-process-params [numeracao_unica]
  (let [numero (strs/replace numeracao_unica #"\.|-" "")
        numeroDigitoAnoUnificado (subs numero 0 13)
        foroNumeroUnificado (subs numero 16)
        ]

    hash-map {:query-params {
                       :conversationId ""
                       :dadosConsulta.localPesquisa.cdLocal "-1"
                       :cbPesquisa "NUMPROC"
                       :dadosConsulta.tipoNuProcesso "UNIFICADO"
                       :numeroDigitoAnoUnificado numeroDigitoAnoUnificado
                       :forumNumeroUnificado foroNumeroUnificado
                       :dadosConsulta.valorConsultaNuUnificado numero
                       :dadosConsulta.valorConsulta ""
                       }
              :insecure? true
              }))

(defn get-dom
  [url numeracao_unica]
  (html/html-snippet
    (:body @(http/get url (get-process-params numeracao_unica)))))

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

(defn get-movs-content [tr-text]
  (strs/trim (get  (strs/split tr-text #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} ") 1))
  )

(defn get-movs-date [tr-text]
  "Find the mov date"
  (first (re-seq #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}" tr-text))
  )

(defn not-blank? [str]
  (not (strs/blank? str))
  )


(defn clean [texto]
  "Clean the text of a mov"
  (strs/replace texto #"\t|\n" ""))

(defn get-partes-info [tr]
    (let [parte-text (clean (html/text tr))
          partes-advogados-list (filterv not-blank? (strs/split parte-text #"  " ))

          ]
      partes-advogados-list
      )
  )


(defn get-partes [dom]

  (let [tabela (html/select dom [:table#tableTodasPartes])
        trs (get-trs tabela)
        partes (map get-partes-info trs)
        ]
    partes
    )
  )

(defn get-movimentacoes [dom]

  (let [movs-content (get-movs-info dom)
        trs-content (get-trs movs-content)
        text-content (map html/text trs-content)
        cleaned-text (map clean text-content)
        movs-date (vec (map get-movs-date cleaned-text))
        movs-content (vec (map get-movs-content cleaned-text))
        lista-movimentacoes (map vector movs-date movs-content)
        ]
    lista-movimentacoes
    )
  )

(defn -main [numeracao_unica]
  (let [dom (get-dom url numeracao_unica)
        partes (get-partes dom )
        lista-movimentacoes (get-movimentacoes dom)
        dados-processo (hash-map :movimentacoes lista-movimentacoes
                                 :partes partes)
        ]
   (println (get dados-processo :movimentacoes))

    )
  )
