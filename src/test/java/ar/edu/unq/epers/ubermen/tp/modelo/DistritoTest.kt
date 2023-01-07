package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class DistritoTest {

    lateinit var distrito : Distrito
    lateinit var coordenada1 : GeoJsonPoint
    lateinit var coordenada2 : GeoJsonPoint
    lateinit var coordenada3 : GeoJsonPoint
    lateinit var coordenada4 : GeoJsonPoint
    lateinit var coordenada5 : GeoJsonPoint
    lateinit var coordenadas : MutableList<GeoJsonPoint>

    @BeforeEach
    fun prepare() {

        coordenada1 = GeoJsonPoint(-34.70994488483138, -58.26176537112486)
        coordenada2 = GeoJsonPoint(-34.70034861628726,  -58.28545464149768)
        coordenada3 = GeoJsonPoint(-34.713754859185414, -58.30167664186167)
        coordenada4 = GeoJsonPoint(-34.72176274424697, -58.28543392774067)
        coordenada5 = GeoJsonPoint(-34.70994488483138, -58.26176537112486)

        coordenadas = mutableListOf<GeoJsonPoint>()
        coordenadas.add(coordenada1)
        coordenadas.add(coordenada2)
        distrito = Distrito("Berazategui", coordenadas)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("Berazategui", distrito.nombre)
        Assertions.assertEquals(2, distrito.coordenadas!!.points.size)
    }

    @Test
    fun seAgreganCoordenadasADistritoTest() {
        Assertions.assertEquals(2, distrito.coordenadas!!.points.size)
        Assertions.assertTrue(distrito.coordenadas!!.contains(coordenada1))

        distrito.agregarCoordenada(coordenada3)
        distrito.agregarCoordenada(coordenada4)
        distrito.agregarCoordenada(coordenada5)

        Assertions.assertEquals(5, distrito.coordenadas!!.points.size)
    }

    @Test
    fun seEliminanCoordenadasADistritoTest() {
        Assertions.assertEquals(2, distrito.coordenadas!!.points.size)
        Assertions.assertTrue(distrito.coordenadas!!.contains(coordenada1))
        Assertions.assertTrue(distrito.coordenadas!!.contains(coordenada2))

        distrito.eliminarCoordenada(coordenada2)

        Assertions.assertEquals(1, distrito.coordenadas!!.points.size)
    }
}