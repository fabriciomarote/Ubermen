package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class CantidadConflictos() {
    @Id
    var idDistrito: String? = null
    var cantConflictos:Int = 0
}