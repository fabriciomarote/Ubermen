package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("Conflicto")
class ConflictoMongo {
    @Id
    var id: String? = null

    @GeoSpatialIndexed(name = "coordenada", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordenada : GeoJsonPoint? = null

    var resuelto : Boolean = false

    var idDistrito : String? = null

    var idConflicto : Long? = null

    protected constructor() {
    }

    constructor(id: Long, coordenada: GeoJsonPoint, idDistrito : String) {
        this.idConflicto = id
        this.coordenada = coordenada
        this.idDistrito = idDistrito
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConflictoMongo

        if (id != other.id) return false
        if (coordenada != other.coordenada) return false
        if (resuelto != other.resuelto) return false

        return true
    }
}