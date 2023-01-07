package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed

class Distrito {
    @Id
    var id: String? = null

    var nombre: String? = null

    @GeoSpatialIndexed(name = "coordenadas", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordenadas: GeoJsonPolygon? = null

    protected constructor() {
    }

    constructor(nombre : String, coordenadas : List<GeoJsonPoint>) {
        this.nombre = nombre
        this.coordenadas = GeoJsonPolygon(coordenadas)
    }

    fun agregarCoordenada(coordenada: GeoJsonPoint) {
        var coordenadaPoint = Point(coordenada.x, coordenada.y)
        var listaCoordenadas : List<Point> = this.coordenadas!!.points.toList()
        listaCoordenadas += coordenadaPoint
        this.coordenadas = GeoJsonPolygon(listaCoordenadas)
    }

    fun eliminarCoordenada(coordenada: GeoJsonPoint) {
        var coordenadaPoint = Point(coordenada.x, coordenada.y)
        var listaCoordenadas : List<Point> = this.coordenadas!!.points.toList()
        if (listaCoordenadas.contains(coordenadaPoint)) {
            listaCoordenadas -= coordenadaPoint
            if(listaCoordenadas.isEmpty()) {
                this.coordenadas = null
            } else {
                this.coordenadas = GeoJsonPolygon(listaCoordenadas)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Distrito

        if (id != other.id) return false
        if (nombre != other.nombre) return false

        return true
    }
}