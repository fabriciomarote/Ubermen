package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class HeroeMongoTest {

    lateinit var heroeMongo : HeroeMongo
    lateinit var coordenada1 : GeoJsonPoint
    lateinit var coordenada2 : GeoJsonPoint

    @BeforeEach
    fun prepare() {

        coordenada1 = GeoJsonPoint(-34.70994488483138, -58.26176537112486)
        coordenada2 = GeoJsonPoint(-34.70034861628726,  -58.28545464149768)
        heroeMongo = HeroeMongo(1, coordenada1, "2")
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals(1, heroeMongo.idPersonaje)
        Assertions.assertEquals(coordenada1, heroeMongo.coordenada)
        Assertions.assertEquals("2", heroeMongo.idDistrito)
    }

    @Test
    fun cambiarCoordenadaAHeroeMongoTest() {
        Assertions.assertEquals(coordenada1, heroeMongo.coordenada)

        heroeMongo.cambiarCoordenada(coordenada2, "2")

        Assertions.assertEquals(coordenada2, heroeMongo.coordenada)
        Assertions.assertEquals("2", heroeMongo.idDistrito)
    }
}