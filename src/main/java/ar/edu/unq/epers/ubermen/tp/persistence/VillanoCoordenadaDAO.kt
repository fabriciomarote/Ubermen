package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.VillanoMongo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface VillanoCoordenadaDAO : MongoRepository<VillanoMongo, String> {
    @Query("{idPersonaje: ?0}")
    fun findByIdSQL(id : Long) : VillanoMongo
}