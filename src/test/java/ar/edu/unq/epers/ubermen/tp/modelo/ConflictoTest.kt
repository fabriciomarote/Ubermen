package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class ConflictoTest {
    lateinit var conflicto : Conflicto
    lateinit var prueba : PruebaDeHabilidad
    lateinit var poder : Poder
    var constitucion : String = "constitucion"

    @BeforeEach
    fun prepare() {
        conflicto = Conflicto("conflictoNombre")
        prueba = PruebaDeHabilidad("prueba1", 4, 3, 5, Atributo.FUERZA)
        poder = Poder("poderNombre", Atributo.CONSTITUCION, 10)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("conflictoNombre", conflicto.nombre)
        Assertions.assertEquals(0, conflicto.progresoHaciaSuResolucion)
        Assertions.assertEquals(0, conflicto.pruebas.size)
        Assertions.assertEquals(0, conflicto.poderesFavorables.size)
        Assertions.assertEquals(null, conflicto.resueltoPor)
        Assertions.assertEquals(null, conflicto.creadoPor)
    }

    @Test
    fun cambiarNombreAConflictoTest() {
        Assertions.assertEquals("conflictoNombre", conflicto.nombre)

        conflicto.cambiarNombre("nuevoNombre")
        Assertions.assertEquals("nuevoNombre", conflicto.nombre)
    }

    @Test
    fun cambiarNombreVacioAConflictoTest() {
        Assertions.assertEquals("conflictoNombre", conflicto.nombre)
        try {
            conflicto.cambiarNombre("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }


    @Test
    fun incrementarProgresoEn1AConflictoTest() {
        Assertions.assertEquals(0, conflicto.progresoHaciaSuResolucion)

        conflicto.incrementarProgreso(1)
        Assertions.assertEquals(1, conflicto.progresoHaciaSuResolucion)
    }

    @Test
    fun incrementarProgresoConNumeroNegativoAConflictoTest() {
        Assertions.assertEquals(0, conflicto.progresoHaciaSuResolucion)
        try {
            conflicto.incrementarProgreso(-50)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun reiniciarProgresoAConflictoTest() {
        conflicto.incrementarProgreso(5)
        Assertions.assertEquals(5, conflicto.progresoHaciaSuResolucion)

        conflicto.reiniciarProgreso()
        Assertions.assertEquals(0, conflicto.progresoHaciaSuResolucion)
    }

    @Test
    fun agregarUnaPruebaAConflictoTest() {
        Assertions.assertEquals(0, conflicto.pruebas.size)

        conflicto.agregarPrueba(prueba)
        Assertions.assertEquals(1, conflicto.pruebas.size)
        Assertions.assertTrue(conflicto.pruebas.contains(prueba))
    }

    @Test
    fun eliminarUnaPruebaAConflictoTest() {
        conflicto.agregarPrueba(prueba)
        Assertions.assertEquals(1, conflicto.pruebas.size)
        Assertions.assertTrue(conflicto.pruebas.contains(prueba))

        conflicto.eliminarPrueba(prueba)
        Assertions.assertEquals(0, conflicto.pruebas.size)
        Assertions.assertFalse(conflicto.pruebas.contains(prueba))
    }

    @Test
    fun agregarUnaPoderFavorableAConflictoTest() {
        Assertions.assertEquals(0, conflicto.poderesFavorables.size)

        conflicto.agregarPoderFavorable(poder)
        Assertions.assertEquals(1, conflicto.poderesFavorables.size)
        Assertions.assertTrue(conflicto.poderesFavorables.contains(poder))
    }

    @Test
    fun eliminarUnPoderFavorableAConflictoTest() {
        conflicto.agregarPoderFavorable(poder)
        Assertions.assertEquals(1, conflicto.poderesFavorables.size)
        Assertions.assertTrue(conflicto.poderesFavorables.contains(poder))

        conflicto.eliminarPoderFavorable(poder)
        Assertions.assertEquals(0, conflicto.poderesFavorables.size)
        Assertions.assertFalse(conflicto.poderesFavorables.contains(poder))
    }
}