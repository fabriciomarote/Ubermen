package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.persistence.PruebaDeHabilidadDAO
import ar.edu.unq.epers.ubermen.tp.service.impl.PruebaDeHabilidadServiceImp
import org.junit.jupiter.api.*
//import io.kotlintest.shouldThrow
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.RuntimeException

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class PruebaDeHabilidadServiceTest {

    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    lateinit var service: PruebaDeHabilidadService
    lateinit var pruebaDeHabilidad1: PruebaDeHabilidad
    lateinit var pruebaDeHabilidad2: PruebaDeHabilidad
    lateinit var pruebaDeHabilidad3: PruebaDeHabilidad
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA
    var inteligencia: Atributo = Atributo.INTELIGENCIA

    @BeforeEach
    fun prepare() {
        this.service = PruebaDeHabilidadServiceImp(
            pruebaDeHabilidadDAO
        )

        pruebaDeHabilidad1 = PruebaDeHabilidad("prueba1", 5, 2, 5, fuerza)
        pruebaDeHabilidad2 = PruebaDeHabilidad("prueba2", 3, 3, 4, inteligencia)
    }

    @Test
    fun seCreaPrueba1Test() {
        var prueba = service.crear(pruebaDeHabilidad1)
        var pruebaId = prueba.id!!.toInt()

        var pruebaRecuperada = service.recuperar(pruebaId)

        Assertions.assertEquals(pruebaRecuperada, pruebaDeHabilidad1)

        Assertions.assertEquals("prueba1", pruebaRecuperada.nombre)
        Assertions.assertEquals(5, pruebaRecuperada.dificultad)
        Assertions.assertEquals(2, pruebaRecuperada.cantidadDeExitos)
        Assertions.assertEquals(5, pruebaRecuperada.retalacion)
        Assertions.assertEquals( fuerza, pruebaRecuperada.atributoDesafiado)

        service.eliminar(pruebaId)
    }

    @Test
    fun NoSePuedeCrearPruebaConDificultadNegativaTest(){
        pruebaDeHabilidad3 = PruebaDeHabilidad("prueba3", -3, 2, 2, destreza)
        try {
            service.crear(pruebaDeHabilidad3)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun NoSePuedeCrearPruebaConCantidadDeExitosNegativaTest(){
        pruebaDeHabilidad3 = PruebaDeHabilidad("prueba3", 3, -2, 2, destreza)
        try {
            service.crear(pruebaDeHabilidad3)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun NoSePuedeCrearPruebaConRetalacionNegativaTest(){
        pruebaDeHabilidad3 = PruebaDeHabilidad("prueba3", 1, 2, -10, destreza)
        try {
            service.crear(pruebaDeHabilidad3)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun seActualizaElNombreDeLaPruebaTest() {
        var pruebaDeHabilidad2BD = service.crear(pruebaDeHabilidad2)
        var pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaRecuperado = service.recuperar(pruebaId)

        Assertions.assertEquals("prueba2", pruebaRecuperado.nombre)

        pruebaDeHabilidad2.cambiarNombre("Prueba N 3")

        pruebaDeHabilidad2BD = service.actualizar(pruebaDeHabilidad2)

        pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaActualizada = service.recuperar(pruebaId)

        Assertions.assertEquals("Prueba N 3", pruebaActualizada.nombre)

        service.eliminar(pruebaId)
    }

    @Test
    fun seActualizaLaDificultadDeLaPruebaTest() {
        var pruebaDeHabilidad2BD = service.crear(pruebaDeHabilidad2)
        var pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaRecuperado = service.recuperar(pruebaId)

        Assertions.assertEquals(3, pruebaRecuperado.dificultad)

        pruebaDeHabilidad2.cambiarDificultad(6)

        pruebaDeHabilidad2BD = service.actualizar(pruebaDeHabilidad2)

        pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaActualizada = service.recuperar(pruebaId)

        Assertions.assertEquals(6, pruebaActualizada.dificultad)

        service.eliminar(pruebaId)
    }

    @Test
    fun noSePuedeActualizarDificultadDePruebaConNumeroNegativoTest() {
        var prueba = pruebaDeHabilidad1
        service.crear(prueba)

        try {
            prueba.cambiarDificultad(-1)
            service.actualizar(prueba)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun seActualizaYCambiaLaCantidadDeExitosDeLaPruebaTest() {
        var pruebaDeHabilidad2BD = service.crear(pruebaDeHabilidad2)
        var pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaRecuperado = service.recuperar(pruebaId)

        Assertions.assertEquals(3, pruebaRecuperado.cantidadDeExitos)

        pruebaDeHabilidad2.cambiarCantidadDeExitos(5)

        pruebaDeHabilidad2BD = service.actualizar(pruebaDeHabilidad2)

        pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaActualizada = service.recuperar(pruebaId)

        Assertions.assertEquals(5, pruebaActualizada.cantidadDeExitos)

        service.eliminar(pruebaId)
    }

    @Test
    fun seActualizaLaCantidadDeExitosDeLaPruebaTest() {
        var pruebaDeHabilidad2BD = service.crear(pruebaDeHabilidad2)
        var pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaRecuperado = service.recuperar(pruebaId)

        Assertions.assertEquals(3, pruebaRecuperado.cantidadDeExitos)

        pruebaDeHabilidad2.sumarCantidadDeExitos(2)

        pruebaDeHabilidad2BD = service.actualizar(pruebaDeHabilidad2)

        pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaActualizada = service.recuperar(pruebaId)

        Assertions.assertEquals(5, pruebaActualizada.cantidadDeExitos)

        service.eliminar(pruebaId)
    }

    @Test
    fun noSePuedeActualizarCantidadDeExitosDePruebaConNumeroNegativoTest() {
        var prueba = pruebaDeHabilidad1
        service.crear(prueba)

        try {
            prueba.cambiarCantidadDeExitos(-5)
            service.actualizar(prueba)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun seActualizaLaRetalacionDeLaPruebaTest() {
        var pruebaDeHabilidad2BD = service.crear(pruebaDeHabilidad2)
        var pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaRecuperado = service.recuperar(pruebaId)

        Assertions.assertEquals(4, pruebaRecuperado.retalacion)

        pruebaDeHabilidad2.cambiarRetalacion(10)

        pruebaDeHabilidad2BD = service.actualizar(pruebaDeHabilidad2)

        pruebaId = pruebaDeHabilidad2BD.id!!.toInt()
        var pruebaActualizada = service.recuperar(pruebaId)

        Assertions.assertEquals(10, pruebaActualizada.retalacion)

        service.eliminar(pruebaId)
    }

    @Test
    fun noSePuedeActualizarRetalacionDePruebaConNumeroNegativoTest() {
        var prueba = pruebaDeHabilidad1
        service.crear(prueba)

        try {
            prueba.cambiarRetalacion(-10)
            service.actualizar(prueba)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun seRecuperaPrueba2Test() {
        var prueba = service.crear(pruebaDeHabilidad2)
        val pruebaId = prueba.id!!.toInt()
        val pruebaRecuperada = service.recuperar(pruebaId)

        Assertions.assertEquals(pruebaDeHabilidad2, pruebaRecuperada)

        Assertions.assertEquals("prueba2", pruebaRecuperada.nombre)
        Assertions.assertEquals(3, pruebaRecuperada.dificultad)
        Assertions.assertEquals(3, pruebaRecuperada.cantidadDeExitos)
        Assertions.assertEquals(4, pruebaRecuperada.retalacion)
        Assertions.assertEquals( inteligencia, pruebaRecuperada.atributoDesafiado)

        service.eliminar(pruebaId)
    }

    @Test
    fun noSePuedeRecuperarUnaPruebaConIdInexistenteTest() {
        val pruebaId = 55
        try {
            service.recuperar(pruebaId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "La prueba de habilidad no existe.")
        }
    }

    @Test
    fun recuperarTodasLasPruebasTest() {
        service.crear(pruebaDeHabilidad1)
        service.crear(pruebaDeHabilidad2)

        var pruebasRecuperadas = service.recuperarTodos()
        Assertions.assertEquals(2, pruebasRecuperadas.size)
        Assertions.assertTrue(pruebasRecuperadas.contains(pruebaDeHabilidad1))
        Assertions.assertTrue(pruebasRecuperadas.contains(pruebaDeHabilidad2))

        service.eliminar(pruebaDeHabilidad2.id!!.toInt())
        pruebasRecuperadas = service.recuperarTodos()
        Assertions.assertEquals(1, pruebasRecuperadas.size)
        Assertions.assertFalse(pruebasRecuperadas.contains(pruebaDeHabilidad2))
        Assertions.assertTrue(pruebasRecuperadas.contains(pruebaDeHabilidad1))
    }

    @Test
    fun recuperarTodasLasPruebasVacioTest() {
        Assertions.assertTrue(service.recuperarTodos().isEmpty())
    }

    @Test
    fun eliminarUnaPruebaTest() {
        var prueba2 = service.crear(pruebaDeHabilidad2)
        val pruebaId = prueba2.id!!.toInt()
        val pruebaRecuperada = service.recuperar(pruebaId)

        Assertions.assertEquals(prueba2, pruebaRecuperada)
        Assertions.assertTrue(service.recuperarTodos().contains(prueba2))

        service.eliminar(pruebaId)

        // Se comprueba que prueba2 fue eliminada
        Assertions.assertFalse(service.recuperarTodos().contains(prueba2))

        try {
            service.recuperar(pruebaId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "La prueba de habilidad no existe.")
        }
    }

    @Test
    fun noSePuedeEliminarUnaPruebaQueNoExisteTest() {
        val pruebaId = 33
        try {
            service.recuperar(pruebaId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "La prueba de habilidad no existe.")
        }
    }



    @AfterEach
    fun eliminarTodo() {
        service.clear()
    }
}