package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.Document

@Document("Heroe")
class HeroeMongo : PersonajeMongo {
    protected constructor() {}

    constructor(id: Long) : this() {
        this.idPersonaje = id
    }

    constructor(id: Long, coordenada: GeoJsonPoint, idDistrito : String) : this() {
        this.idPersonaje = id
        this.coordenada = coordenada
        this.idDistrito = idDistrito
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeroeMongo

        if (id != other.id) return false
        if (coordenada != other.coordenada) return false

        return true
    }
}