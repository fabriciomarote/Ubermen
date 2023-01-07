package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.PoderNeo
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface PoderMutacionDAO : Neo4jRepository<PoderNeo, Long?> {

    @Query("MATCH(n) DETACH DELETE n")
    fun detachDelete()

    @Query("""
        MATCH(heroeNeo:HeroeNeo {id: ${'$'}idHeroe})-[p:PODER]->
        (poderNeo:PoderNeo)-[CONDICION]->(c:Condicion)
        MATCH(heroeNeo)-[cont:CONTADOR]->(c)
        WHERE cont.cantidad >= c.cantidad
        RETURN poderNeo
    """)
    fun poderesQuePuedenMutar(idHeroe: Long) : Set<PoderNeo>

    @Query("""
        MATCH(heroeNeo:HeroeNeo {id: ${'$'}idHeroe})-[cont:CONTADOR]->(c:Condicion)<-[CONDICION]-(poderNeo:PoderNeo {id:${'$'}idPoder}),
        (c)-[TARGET_NODE]->(poderNeoAMutar:PoderNeo)
        WHERE (cont.cantidad >= c.cantidad) AND NOT EXISTS {
          MATCH (heroeNeo)-[:PODER]->(poderNeoAMutar)
        }
        RETURN poderNeoAMutar
    """)
    fun mutacionesHabilitadas(idHeroe:Long, idPoder:Long) : Set<PoderNeo>

    @Query("""
        MATCH path=shortestPath((poderNeoInicial:PoderNeo {id:${'$'}idPoder1})-[*]->(poderNeoFinal:PoderNeo {id:${'$'}idPoder2}))
        WHERE ALL(c in nodes(path) WHERE NOT 'Condicion' IN LABELS(c) or (c.atributo IN ${'$'}atributos))
        RETURN path 
    """)
    fun caminoMasRentable(idPoder1: Long, idPoder2: Long, atributos: Set<Atributo>): List<PoderNeo>
}
