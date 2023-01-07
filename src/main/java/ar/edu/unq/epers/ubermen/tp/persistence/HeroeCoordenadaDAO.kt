package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.CantidadHeroes
import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface HeroeCoordenadaDAO : MongoRepository<HeroeMongo, String> {

    @Query("{idPersonaje: ?0}")
    fun findByIdSQL(id : Long) : HeroeMongo

    @Aggregation(pipeline = [
        "{\$group: {_id: \$idDistrito, 'cantHeroes': { \$sum: 1 }  }}",
        "{\$sort: {'cantHeroes': -1  }}",
        "{\$limit: 1}",
    ])
    fun masVigilado() : List<CantidadHeroes>
}