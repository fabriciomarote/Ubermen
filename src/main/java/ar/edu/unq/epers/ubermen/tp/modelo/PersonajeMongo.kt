package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed

abstract class PersonajeMongo {
    @Id
    var id: String? = null

    var idPersonaje: Long? = null

    var idDistrito : String? = null

    @GeoSpatialIndexed(name = "coordenada", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordenada : GeoJsonPoint? = null

    protected constructor() {
    }

    constructor(id: Long) {
        this.idPersonaje = id
    }

    constructor(id: Long, coordenada: GeoJsonPoint, idDistrito: String) {
        this.idPersonaje = id
        this.coordenada = coordenada
        this.idDistrito = idDistrito
    }

    fun cambiarCoordenada(coordenada: GeoJsonPoint, idDistrito: String) {
        this.coordenada = coordenada
        this.idDistrito = idDistrito
    }
}