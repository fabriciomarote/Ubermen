package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class PoderNeo() {
    @Id
    var id: Long? = null

    var nombre : String? = null

    @Relationship(type = "CONDICION")
    var condiciones : List<Condicion> = emptyList()

    constructor(id: Long, nombre: String) : this() {
        this.id = id
        this.nombre = nombre
    }

    fun fromPoder(poder : Poder) : PoderNeo {
        this.id = poder.id
        this.nombre = poder.nombre
        return this
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (nombre?.hashCode() ?: 0)
        return result
    }

    fun agregarCondicion(condicion : Condicion) {
        this.condiciones += condicion
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PoderNeo

        if (id != other.id) return false
        if (nombre != other.nombre) return false

        return true
    }
}