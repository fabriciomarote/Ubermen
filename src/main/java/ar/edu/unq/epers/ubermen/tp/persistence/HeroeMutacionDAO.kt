package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeNeo
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface HeroeMutacionDAO : Neo4jRepository<HeroeNeo, Long?> {

    @Query("MATCH(n) DETACH DELETE n")
    fun detachDelete()
}