package br.grupointegrado.tads.metas

import java.text.SimpleDateFormat
import java.util.*

class ModMeta {

    constructor(titulo: String, descricao: String, dtlimite: Date, concluido: Boolean){
        this.titulo = titulo
        this.descricao = descricao
        this.dtlimite = dtlimite
        this.dtconclusao = null
        this.concluido = concluido
    }

    constructor(titulo: String, descricao: String, dtlimite: Date, dtconclusao: Date?, concluido: Boolean){
        this.titulo = titulo
        this.descricao = descricao
        this.dtlimite = dtlimite
        this.dtconclusao = dtconclusao
        this.concluido = concluido
    }

    var titulo: String
    var descricao: String
    var dtlimite: Date
    var dtconclusao: Date?
    var concluido: Boolean

}