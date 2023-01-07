package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class CantidadHeroes() {
    @Id
    var idDistrito: String? = null
    var cantHeroes:Int = 0
}