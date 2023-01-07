package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HeroeTest {

    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
    var conflictos: MutableSet<Conflicto> = mutableSetOf<Conflicto>()
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA
    var superFuerza : Poder = Poder("Super Fuerza", fuerza, 10)
    var volar : Poder = Poder("Volar", destreza, 5)
    var rapidez : Poder = Poder("Rapidez", destreza, 15)
    var regeneramiento : Poder = Poder("Regeneramiento", constitucion, 20)
    var conflicto1 : Conflicto = Conflicto("conflicto1")
    var conflicto2 : Conflicto = Conflicto("conflicto1")
    lateinit var superMan: Heroe

    @BeforeEach
    fun prepare() {

        poderes = mutableSetOf<Poder>()
        poderes.add(superFuerza)
        poderes.add(regeneramiento)
        poderes.add(volar)
        superMan = Heroe("Superman", 100, "imagen", conflictos, poderes)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals("Superman", superMan.nombre)
        Assertions.assertEquals(100, superMan.vida)
        Assertions.assertEquals("imagen", superMan.imagenURL)
        Assertions.assertEquals(0, superMan.conflictosResueltos.size)
        Assertions.assertEquals(3, superMan.poderes.size)
        Assertions.assertEquals(21, superMan.getConstitucion())
        Assertions.assertEquals(11, superMan.getFuerza())
        Assertions.assertEquals(1, superMan.getInteligencia())
        Assertions.assertEquals(6, superMan.getDestreza())
    }

    @Test
    fun cambiarNombreASuperManTest() {
        Assertions.assertEquals("Superman", superMan.nombre)

        superMan.cambiarNombre("super Man")

        Assertions.assertEquals("super Man", superMan.nombre)
    }

    @Test
    fun noSePuedeCambiarNombreVacioASuperManTest() {
        Assertions.assertEquals("Superman", superMan.nombre)
        try {
            superMan.cambiarNombre("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun cambiarVidaASuperManDe100a70Test() {
        Assertions.assertEquals(100, superMan.vida)

        superMan.cambiarVida(70)

        Assertions.assertEquals(70, superMan.vida)
    }

    @Test
    fun noSePuedeCambiarVidaDeHeroeConValorNegativoTest() {
        Assertions.assertEquals(100, superMan.vida)
        try {
            superMan.cambiarVida(-100)
            Assertions.fail("Expected a CantidadNegativaException to be thrown")
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun sumarVidaAHeroeDe10a30Test() {
        val heroe = Heroe("Heroe", 10, "imagen", conflictos, poderes)
        Assertions.assertEquals(10, heroe.vida)

        heroe.sumarVida(20)

        Assertions.assertEquals(30, heroe.vida)
    }

    @Test
    fun noSePuedeSumarVidaDeHeroeMayorA100Test() {
        val heroe = Heroe("Heroe", 80, "imagen", conflictos, poderes)
        Assertions.assertEquals(80, heroe.vida)

        heroe.sumarVida(30)

        // La vida se suma pero no supera a 100 porque el maximo de vida de un heroe es 100
        Assertions.assertEquals(100, heroe.vida)
    }

    @Test
    fun RestarVidaASuperManDe100a20Test() {
        Assertions.assertEquals(100, superMan.vida)

        superMan.restarVida(80)

        Assertions.assertEquals(20, superMan.vida)
    }

    @Test
    fun RestarVidaAHeroeDe20a0YEsteMuereTest() {
        val heroe = Heroe("Heroe", 20, "imagen", conflictos, poderes)
        Assertions.assertEquals(20, heroe.vida)
        Assertions.assertFalse(heroe.estaMuerto())

        heroe.restarVida(20)

        Assertions.assertEquals(0, heroe.vida)
        Assertions.assertTrue(heroe.estaMuerto())
    }

    @Test
    fun noSePuedeRestarVidaDeHeroeMenorA0Test() {
        val heroe = Heroe("Heroe", 20, "imagen", conflictos, poderes)
        Assertions.assertEquals(20, heroe.vida)
        Assertions.assertFalse(heroe.estaMuerto())

        heroe.restarVida(30)

        // La vida no va a ser menor a 0 porque ese es el minimo de vida de un heroe, una vez que llega a 0 el heroe muere.
        Assertions.assertEquals(0, heroe.vida)
        Assertions.assertTrue(heroe.estaMuerto())
    }

    @Test
    fun cambiarImagenASuperManTest() {
        Assertions.assertEquals("imagen", superMan.imagenURL)

        superMan.cambiarImagen("SuperManImagen")

        Assertions.assertEquals("SuperManImagen", superMan.imagenURL)
    }

    @Test
    fun noSePuedeCambiarImagenUrlVacioASuperManTest() {
        Assertions.assertEquals("imagen", superMan.imagenURL)
        try {
            superMan.cambiarImagen("")
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seAgreganConflictosAHeroe() {
        Assertions.assertEquals(0, superMan.conflictosResueltos.size)

        superMan.agregarConflicto(conflicto1)
        superMan.agregarConflicto(conflicto2)

        Assertions.assertEquals(2, superMan.conflictosResueltos.size)
    }

    @Test
    fun seEliminaConflictoAHeroe() {
        Assertions.assertEquals(0, superMan.conflictosResueltos.size)

        superMan.agregarConflicto(conflicto1)
        superMan.agregarConflicto(conflicto2)

        Assertions.assertEquals(2, superMan.conflictosResueltos.size)
        Assertions.assertTrue(superMan.conflictosResueltos.contains(conflicto1))
        Assertions.assertTrue(superMan.conflictosResueltos.contains(conflicto2))

        superMan.eliminarConflicto(conflicto1)

        Assertions.assertEquals(1, superMan.conflictosResueltos.size)
        Assertions.assertFalse(superMan.conflictosResueltos.contains(conflicto1))
        Assertions.assertTrue(superMan.conflictosResueltos.contains(conflicto2))
    }

    @Test
    fun heroePoseePoderVolarYSuDestrezaSeModificaDe6a21Test() {
        Assertions.assertEquals(3, superMan.poderes.size)
        Assertions.assertEquals(6, superMan.getDestreza())

        superMan.poseerPoder(rapidez)

        Assertions.assertEquals(4, superMan.poderes.size)
        Assertions.assertEquals(21, superMan.getDestreza())
    }

    @Test
    fun heroeOlvidaPoderYSuDestrezaSeModificaDe6A1Test() {
        Assertions.assertEquals(3, superMan.poderes.size)
        Assertions.assertEquals(6, superMan.getDestreza())
        Assertions.assertEquals(21, superMan.getConstitucion())
        Assertions.assertEquals(11, superMan.getFuerza())

        superMan.olvidarPoder(volar)

        Assertions.assertEquals(2, superMan.poderes.size)
        Assertions.assertEquals(1, superMan.getDestreza())
        Assertions.assertEquals(21, superMan.getConstitucion())
        Assertions.assertEquals(11, superMan.getFuerza())
    }

}