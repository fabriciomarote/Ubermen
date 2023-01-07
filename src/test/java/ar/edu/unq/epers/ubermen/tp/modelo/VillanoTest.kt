package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VillanoTest {

    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
    var conflictosComenzados: MutableSet<Conflicto> = mutableSetOf<Conflicto>()
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA
    var fuerzaMaligna : Poder = Poder("Fuerza Maligna", fuerza, 30)
    var volar : Poder = Poder("Volar", destreza, 15)
    var regeneramiento : Poder = Poder("Regeneramiento", constitucion, 40)
    var conflicto1 : Conflicto = Conflicto("conflicto1")
    var conflicto2 : Conflicto = Conflicto("conflicto1")
    lateinit var thanos: Villano

    @BeforeEach
    fun prepare() {

        poderes = mutableSetOf<Poder>()
        poderes.add(fuerzaMaligna)
        thanos = Villano("Thanos", 100, "imagen", conflictosComenzados, poderes)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("Thanos", thanos.nombre)
        Assertions.assertEquals(100, thanos.vida)
        Assertions.assertEquals("imagen", thanos.imagenURL)
        Assertions.assertEquals(0, thanos.conflictosComenzados.size)
        Assertions.assertEquals(1, thanos.poderes.size)
        Assertions.assertEquals(1, thanos.getConstitucion())
        Assertions.assertEquals(31, thanos.getFuerza())
        Assertions.assertEquals(1, thanos.getInteligencia())
        Assertions.assertEquals(1, thanos.getDestreza())
    }

    @Test
    fun seCambiarNombreDeVillanoPorTitanTest() {
        Assertions.assertEquals("Thanos", thanos.nombre)

        thanos.cambiarNombre("Titan")

        Assertions.assertEquals("Titan", thanos.nombre)
    }

    @Test
    fun noSePuedeCambiarNombreVacioAThanosTest() {
        Assertions.assertEquals("Thanos", thanos.nombre)

        try {
            thanos.cambiarNombre("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun cambiarVidaAThanosDe100a70Test() {
        Assertions.assertEquals(100, thanos.vida)

        thanos.cambiarVida(70)

        Assertions.assertEquals(70, thanos.vida)
    }

    @Test
    fun noSePuedeCambiarVidaDeVillanoConValorNegativoTest() {
        Assertions.assertEquals(100, thanos.vida)

        try {
            thanos.cambiarVida(-100)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun sumarVidaAVillanoDe10a30Test() {
        val villano = Villano("Villano", 10, "imagen", conflictosComenzados, poderes)
        Assertions.assertEquals(10, villano.vida)

        villano.sumarVida(20)

        Assertions.assertEquals(30, villano.vida)
    }

    @Test
    fun noSePuedeSumarVidaDeVillanoMayorA100Test() {
        val villano = Villano("Thanos", 80, "imagen", conflictosComenzados, poderes)
        Assertions.assertEquals(80, villano.vida)

        villano.sumarVida(30)

        // La vida se suma pero no supera a 100 porque el maximo de vida de un villano es 100
        Assertions.assertEquals(100, villano.vida)
    }

    @Test
    fun RestarVidaAThanosDe100a20Test() {
        Assertions.assertEquals(100, thanos.vida)

        thanos.restarVida(80)

        Assertions.assertEquals(20, thanos.vida)
    }

    @Test
    fun RestarVidaAVillanoDe20a0YEsteMuereTest() {
        val villano = Villano("Villano", 20, "imagen", conflictosComenzados, poderes)
        Assertions.assertEquals(20, villano.vida)
        Assertions.assertFalse(villano.estaMuerto())

        villano.restarVida(20)

        Assertions.assertEquals(0, villano.vida)
        Assertions.assertTrue(villano.estaMuerto())
    }

    @Test
    fun noSePuedeRestarVidaDeVillanoMenorA0Test() {
        val villano = Villano("Villano", 20, "imagen", conflictosComenzados, poderes)
        Assertions.assertEquals(20, villano.vida)
        Assertions.assertFalse(villano.estaMuerto())

        villano.restarVida(30)

        // La vida no va a ser menor a 0 porque ese es el minimo de vida de un villano, una vez que llega a 0 el villano muere.
        Assertions.assertEquals(0, villano.vida)
        Assertions.assertTrue(villano.estaMuerto())
    }

    @Test
    fun cambiarImagenAThanosTest() {
        Assertions.assertEquals("imagen", thanos.imagenURL)

        thanos.cambiarImagen("ThanosImagen")

        Assertions.assertEquals("ThanosImagen", thanos.imagenURL)
    }

    @Test
    fun noSePuedeCambiarImagenUrlVacioAThanosTest() {
        Assertions.assertEquals("imagen", thanos.imagenURL)

        try {
            thanos.cambiarImagen("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seAgreganConflictosComenzadosAVillano() {
        Assertions.assertEquals(0, thanos.conflictosComenzados.size)

        thanos.agregarConflicto(conflicto1)
        thanos.agregarConflicto(conflicto2)

        Assertions.assertEquals(2, thanos.conflictosComenzados.size)
    }

    @Test
    fun seEliminaConflictoComenzadoAVillano() {
        Assertions.assertEquals(0, thanos.conflictosComenzados.size)

        thanos.agregarConflicto(conflicto1)
        thanos.agregarConflicto(conflicto2)

        Assertions.assertEquals(2, thanos.conflictosComenzados.size)
        Assertions.assertTrue(thanos.conflictosComenzados.contains(conflicto1))
        Assertions.assertTrue(thanos.conflictosComenzados.contains(conflicto2))

        thanos.eliminarConflicto(conflicto1)

        Assertions.assertEquals(1, thanos.conflictosComenzados.size)
        Assertions.assertFalse(thanos.conflictosComenzados.contains(conflicto1))
        Assertions.assertTrue(thanos.conflictosComenzados.contains(conflicto2))
    }

    @Test
    fun villanoPoseePoderVolarYSuDestrezaSeModificaDe1A6Test() {
        Assertions.assertEquals(1, thanos.poderes.size)
        Assertions.assertEquals(1, thanos.getDestreza())

        thanos.poseerPoder(volar)

        Assertions.assertEquals(2, thanos.poderes.size)
        Assertions.assertEquals(16, thanos.getDestreza())
    }

    @Test
    fun VillanoOlvidaPoderYSuDestrezaSeModificaDe6A1Test() {
        poderes.add(regeneramiento)
        poderes.add(volar)
        val villano = Villano("Thanos", 100, "imagen", conflictosComenzados, poderes)
        Assertions.assertEquals(3, villano.poderes.size)
        Assertions.assertEquals(16, villano.getDestreza())
        Assertions.assertEquals(41, villano.getConstitucion())
        Assertions.assertEquals(31, villano.getFuerza())

        villano.olvidarPoder(volar)

        Assertions.assertEquals(2, villano.poderes.size)
        Assertions.assertEquals(1, villano.getDestreza())
        Assertions.assertEquals(41, villano.getConstitucion())
        Assertions.assertEquals(31, villano.getFuerza())
    }
}