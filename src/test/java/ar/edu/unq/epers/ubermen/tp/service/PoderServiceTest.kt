package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.persistence.PoderDAO
import ar.edu.unq.epers.ubermen.tp.persistence.PoderMutacionDAO
import ar.edu.unq.epers.ubermen.tp.service.impl.PoderServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class PoderServiceTest {

    @Autowired
    lateinit var poderMutacionDAO : PoderMutacionDAO

    @Autowired
    lateinit var poderDAO : PoderDAO
    lateinit var service : PoderService
    lateinit var lanzaRayos : Poder
    lateinit var elasticidad : Poder
    lateinit var superVelocidad : Poder
    lateinit var superFuerza : Poder
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA

    @BeforeEach
    fun prepare() {
        this.service = PoderServiceImp(
            poderDAO, poderMutacionDAO
        )

        lanzaRayos = Poder("lanza Rayos", constitucion, 10)
        elasticidad = Poder("Elasticidad", destreza, 20)
        superVelocidad = Poder("super Velocidad", destreza, 30)
        superFuerza = Poder("Super Fuerza", fuerza, 50)
    }

    @Test
    fun seCreaPoderLanzaRayosTest() {
        var poder = service.crear(lanzaRayos)

        val poderId = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderId)

        Assertions.assertEquals(poder, poderRecuperado)
        Assertions.assertEquals("lanza Rayos", poderRecuperado.nombre)
        Assertions.assertEquals(constitucion, poderRecuperado.atributoPotenciado)
        Assertions.assertEquals(10, poderRecuperado.cantidadPotenciada)

        service.eliminar(poderId)
    }

    @Test
    fun noSePuedeCrearUnPoderConNombreVacioTest() {
        superVelocidad = Poder("", destreza, 30)
        try {
            service.crear(superVelocidad)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seActualizaElNombreAPoderElasticidadTest() {
        var poder = service.crear(elasticidad)

        var poderId = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderId)

        Assertions.assertEquals("Elasticidad", poderRecuperado.nombre)

        poder.cambiarNombre("Nuevo Nombre")

        poder = service.actualizar(poder)
        poderId = poder.id!!.toInt()

        poderRecuperado = service.recuperar(poderId)
        Assertions.assertEquals("Nuevo Nombre", poderRecuperado.nombre)

        service.eliminar(poderId)
    }

    @Test
    fun noSePuedeActualizarUnPoderConNombreVacioTest() {
        var poder = service.crear(elasticidad)

        var poderId = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderId)

        Assertions.assertEquals("Elasticidad", poderRecuperado.nombre)

        try {
            poder.cambiarNombre("")
            service.actualizar(poder)
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seActualizaLaCantidadPotenciadaAPoderElasticidadTest() {
        var poder = service.crear(elasticidad)

        var poderId = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderId)

        Assertions.assertEquals(20, poderRecuperado.cantidadPotenciada)

        poder.cambiarCantidadPotenciada(40)

        poder = service.actualizar(poder)
        poderId = poder.id!!.toInt()

        poderRecuperado = service.recuperar(poderId)
        Assertions.assertEquals(40, poderRecuperado.cantidadPotenciada)

        service.eliminar(poderId)
    }

    @Test
    fun noSePuedeActualizarCantidadPotenciadaConNumeroNegativoVacioAPoderTest() {
        var poder = service.crear(superFuerza)

        var poderId = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderId)

        Assertions.assertEquals(50, poderRecuperado.cantidadPotenciada)

        try {
            poder.cambiarCantidadPotenciada(-10)
            service.actualizar(poder)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun seRecuperaPoderTest() {
        var poder = service.crear(superVelocidad)
        var poderId = poder.id!!.toInt()

        var poderRecuperado = service.recuperar(poderId)

        service.recuperar(poderId)

        Assertions.assertEquals(poder, poderRecuperado)

        service.eliminar(poderId)
    }

    @Test
    fun noSePuedeRecuperarPoderQueNoExisteTest() {
        val poderId = 25
        try {
            service.recuperar(poderId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun recuperarTodos() {
        service.crear(lanzaRayos)
        service.crear(elasticidad)

        Assertions.assertEquals(2, service.recuperarTodos().size)
        Assertions.assertTrue(!service.recuperarTodos().contains(superVelocidad))

        service.crear(superVelocidad)

        Assertions.assertEquals(3, service.recuperarTodos().size)
        Assertions.assertTrue(service.recuperarTodos().contains(lanzaRayos))
        Assertions.assertTrue(service.recuperarTodos().contains(elasticidad))
        Assertions.assertTrue(service.recuperarTodos().contains(superVelocidad))

        service.eliminar(lanzaRayos.id!!.toInt())
        service.eliminar(elasticidad.id!!.toInt())
        service.eliminar(superVelocidad.id!!.toInt())
    }

    @Test
    fun seRecuperanTodosVacioTest() {
        Assertions.assertTrue(service.recuperarTodos().isEmpty())
    }

    @Test
    fun seEliminaUnPoderTest() {
        var poder = service.crear(lanzaRayos)
        var poderID = poder.id!!.toInt()
        var poderRecuperado = service.recuperar(poderID)

        Assertions.assertEquals(poder, poderRecuperado)
        Assertions.assertTrue(service.recuperarTodos().contains(poder))

        service.eliminar(poderID)

        Assertions.assertFalse(service.recuperarTodos().contains(poder))
    }

    @Test
    fun noSePuedeEliminarUnPoderQueNoExisteTest() {
        val poderId = 70
        try {
            service.eliminar(poderId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @AfterEach
    fun cleanup() {
        service.clear()
    }
}