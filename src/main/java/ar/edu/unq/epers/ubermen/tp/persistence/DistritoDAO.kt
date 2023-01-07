package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Distrito
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface DistritoDAO : MongoRepository<Distrito, String> {

    @Aggregation(pipeline = [
        "{\$match: { coordenadas: { \$geoIntersects: {\$geometry: {type: 'Point', coordinates: [?0, ?1] }}}}}"
    ])
    fun distritoDe(x: Double, y: Double) : Distrito
}
