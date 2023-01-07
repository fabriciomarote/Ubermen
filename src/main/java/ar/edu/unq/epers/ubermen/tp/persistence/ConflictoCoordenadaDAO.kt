package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.CantidadConflictos
import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ConflictoCoordenadaDAO : MongoRepository<ConflictoMongo, String> {
    @Query("{idConflicto: ?0}")
    fun findByIdSQL(id : Long) : ConflictoMongo

    @Aggregation(pipeline = [
        "{\$geoNear: { near: { type: 'GeoJsonPoint', coordinates: [?0, ?1] }, distanceField: 'dist.calculated', maxDistance: 1000, query: { resuelto: false }, includeLocs: 'dist.location' }}"
    ])
    fun obtenerConflictoAleatorio(x: Double, y: Double): List<ConflictoMongo>

    @Aggregation(pipeline = [
        "{\$match:{ resuelto: false }}",
        "{\$group: {_id: \$idDistrito, 'cantConflictos': { \$sum: 1 }  }}",
        "{\$sort: {'cantConflictos': -1  }}",
        "{\$limit: 1}",
    ])
    fun masPicante() : List<CantidadConflictos>
}