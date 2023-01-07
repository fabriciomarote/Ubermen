package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
class Contador() {
    @Id
    @GeneratedValue
    var id: Long? = null

    var cantidad : Int = 0

    @TargetNode
    var condicion : Condicion? = null

    constructor(condicion: Condicion) : this() {
        this.condicion = condicion
    }

    fun incrementarContador() {
        this.cantidad++
    }

    fun reiniciarContador() {
        this.cantidad = 0
    }

    fun terminoCondicion() : Boolean {
        return this.cantidad >= condicion!!.cantidad!!
    }
}