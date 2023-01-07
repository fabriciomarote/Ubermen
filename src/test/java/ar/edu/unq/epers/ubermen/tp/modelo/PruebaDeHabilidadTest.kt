package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PruebaDeHabilidadTest {
    lateinit var pruebaDeHabilidad: PruebaDeHabilidad
    var fuerza : Atributo = Atributo.FUERZA

    @BeforeEach
    fun prepare() {
        pruebaDeHabilidad = PruebaDeHabilidad("prueba1", 4, 3, 5, fuerza)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("prueba1", pruebaDeHabilidad.nombre)
        Assertions.assertEquals(4, pruebaDeHabilidad.dificultad)
        Assertions.assertEquals(3, pruebaDeHabilidad.cantidadDeExitos)
        Assertions.assertEquals(5, pruebaDeHabilidad.retalacion)
        Assertions.assertEquals(Atributo.FUERZA, pruebaDeHabilidad.atributoDesafiado)
    }

    @Test
    fun ActualizoNombreDePrueba1PorNombreNuevoTest() {
        Assertions.assertEquals("prueba1", pruebaDeHabilidad.nombre)
        pruebaDeHabilidad.cambiarNombre("nombreNuevo")
        Assertions.assertNotEquals("prueba1", pruebaDeHabilidad.nombre)
        Assertions.assertEquals("nombreNuevo", pruebaDeHabilidad.nombre)
    }

    @Test
    fun noSePuedeActualizarNombreVacioAPrueba1Test() {
        Assertions.assertEquals("prueba1", pruebaDeHabilidad.nombre)
        try {
            pruebaDeHabilidad.cambiarNombre("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun ActualizoDificultadDePrueba1De4a6Test() {
        Assertions.assertEquals(4, pruebaDeHabilidad.dificultad)
        pruebaDeHabilidad.cambiarDificultad(6)
        Assertions.assertEquals(6, pruebaDeHabilidad.dificultad)
    }

    @Test
    fun noSePuedeActualizarDificultadConNumeroNegativoVacioAPrueba1Test() {
        Assertions.assertEquals(4, pruebaDeHabilidad.dificultad)
        try {
            pruebaDeHabilidad.cambiarDificultad(-10)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }


    @Test
    fun ActualizoCantidadDeExitosDePrueba1De3a2Test() {
        Assertions.assertEquals(3, pruebaDeHabilidad.cantidadDeExitos)
        pruebaDeHabilidad.cambiarCantidadDeExitos(2)
        Assertions.assertNotEquals(3, pruebaDeHabilidad.cantidadDeExitos)
        Assertions.assertEquals(2, pruebaDeHabilidad.cantidadDeExitos)
    }

    @Test
    fun noSePuedeActualizarCantidadDeExitosConNumeroNegativoVacioAPrueba1Test() {
        Assertions.assertEquals(3, pruebaDeHabilidad.cantidadDeExitos)
        try {
            pruebaDeHabilidad.cambiarCantidadDeExitos(-5)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun ActualizoRetalacionDePrueba1De5a10Test() {
        Assertions.assertEquals(5, pruebaDeHabilidad.retalacion)
        pruebaDeHabilidad.cambiarRetalacion(10)
        Assertions.assertNotEquals(5, pruebaDeHabilidad.retalacion)
        Assertions.assertEquals(10, pruebaDeHabilidad.retalacion)
    }

    @Test
    fun noSePuedeActualizarRetalacionConNumeroNegativoVacioAPrueba1Test() {
        Assertions.assertEquals(5, pruebaDeHabilidad.retalacion)
        try {
            pruebaDeHabilidad.cambiarCantidadDeExitos(-20)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

}