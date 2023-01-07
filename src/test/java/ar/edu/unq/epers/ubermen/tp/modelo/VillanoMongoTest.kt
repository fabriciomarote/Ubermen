package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class VillanoMongoTest {

    lateinit var villanoMongo : VillanoMongo
    lateinit var coordenada1 : GeoJsonPoint
    lateinit var coordenada2 : GeoJsonPoint

    @BeforeEach
    fun prepare() {

        coordenada1 = GeoJsonPoint(-34.69512504462424, -58.32357509151246)
        coordenada2 = GeoJsonPoint(-34.70262686563159, -58.3077669013321)
        villanoMongo = VillanoMongo(5, coordenada1, "7")
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals(5, villanoMongo.idPersonaje)
        Assertions.assertEquals(coordenada1, villanoMongo.coordenada)
        Assertions.assertEquals("7", villanoMongo.idDistrito)
    }

    @Test
    fun cambiarCoordenadaAHeroeMongoTest() {
        Assertions.assertEquals(coordenada1, villanoMongo.coordenada)

        villanoMongo.cambiarCoordenada(coordenada2, "5")

        Assertions.assertEquals(coordenada2, villanoMongo.coordenada)
        Assertions.assertEquals("5", villanoMongo.idDistrito)
    }
}