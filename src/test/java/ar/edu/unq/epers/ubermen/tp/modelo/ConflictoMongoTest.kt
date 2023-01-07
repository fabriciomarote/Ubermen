package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class ConflictoMongoTest {

    lateinit var conflictoMongo : ConflictoMongo
    lateinit var coordenada : GeoJsonPoint

    @BeforeEach
    fun prepare() {

        coordenada = GeoJsonPoint(-34.70994488483138, -58.26176537112486)
        conflictoMongo = ConflictoMongo(1, coordenada, "2")
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals(1, conflictoMongo.idConflicto)
        Assertions.assertEquals(coordenada, conflictoMongo.coordenada)
        Assertions.assertEquals("2", conflictoMongo.idDistrito)
    }
}