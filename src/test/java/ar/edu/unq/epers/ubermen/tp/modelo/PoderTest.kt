package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PoderTest {

    lateinit var poder : Poder
    var constitucion : Atributo = Atributo.CONSTITUCION

    @BeforeEach
    fun prepare() {
        poder = Poder("poderNombre", constitucion, 10)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("poderNombre", poder.nombre)
        Assertions.assertEquals(constitucion, poder.atributoPotenciado)
        Assertions.assertEquals(10, poder.cantidadPotenciada)
    }

    @Test
    fun actualizoNombreAPoderTest() {
        Assertions.assertEquals("poderNombre", poder.nombre)
        poder.cambiarNombre("rayo-x")
        Assertions.assertEquals("rayo-x", poder.nombre)
    }

    @Test
    fun noSePuedeActualizarNombreVacioAPoderTest() {
        Assertions.assertEquals("poderNombre", poder.nombre)
        try {
            poder.cambiarNombre("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun actualizoCantidadPotenciadaAPoderTest() {
        Assertions.assertEquals(10, poder.cantidadPotenciada)
        poder.cambiarCantidadPotenciada(20)
        Assertions.assertEquals(20, poder.cantidadPotenciada)
    }

    @Test
    fun noSePuedeActualizarCantidadPotenciadaConNumeroNegativoVacioAPoderTest() {
        Assertions.assertEquals("poderNombre", poder.nombre)
        try {
            poder.cambiarCantidadPotenciada(-10)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }
}