package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HeroeNeoTest {

    lateinit var heroeNeo: HeroeNeo
    lateinit var condicion1: Condicion
    lateinit var condicion2: Condicion
    lateinit var condicion3: Condicion
    lateinit var poderNeo1: PoderNeo
    lateinit var poderNeo2: PoderNeo

    var pruebasDeHabilidad = Evaluacion.PRUEBASDEHABILIDAD
    var exitosDeTiradas = Evaluacion.EXITOSDETIRADAS
    var fuerza = Atributo.FUERZA
    var destreza = Atributo.DESTREZA
    var constitucion = Atributo.CONSTITUCION
    var superadas = Resultado.SUPERADAS
    var falladas = Resultado.FALLADAS
    var acumulados = Resultado.ACUMULADOS

    @BeforeEach
    fun prepare() {

        heroeNeo = HeroeNeo(1, "Super Man")
        poderNeo1 = PoderNeo(10, "SuperFuerza")
        poderNeo2 = PoderNeo(11, "Rapidez")
        condicion1 = Condicion(2, pruebasDeHabilidad, fuerza, superadas)
        condicion2 = Condicion(2, pruebasDeHabilidad, destreza, falladas)
        condicion3 = Condicion(3, exitosDeTiradas, fuerza, acumulados)

        heroeNeo.agregarCondicion(condicion1)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals(1, heroeNeo.id)
        Assertions.assertEquals("Super Man", heroeNeo.nombre)
        Assertions.assertEquals(1, heroeNeo.contadores.size)
        Assertions.assertEquals(0, heroeNeo.poderes.size)
    }

    @Test
    fun `se agregan condiciones a contador de heroeNeo`() {
        Assertions.assertEquals(1, heroeNeo.contadores.size)

        heroeNeo.agregarCondicion(condicion2)
        heroeNeo.agregarCondicion(condicion3)

        Assertions.assertEquals(3
            , heroeNeo.contadores.size)
    }

    @Test
    fun `no se puede agregar una condicion a contador de heroeNeo porque ya existe`() {
        try {
            heroeNeo.agregarCondicion(condicion1)
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "La condición ya se encuentra en el heroe.")
        }
    }

    @Test
    fun `se eliminan condiciones a contador de heroeNeo`() {
        heroeNeo.agregarCondicion(condicion2)
        Assertions.assertEquals(2, heroeNeo.contadores.size)

        heroeNeo.eliminarCondicion(condicion2)

        Assertions.assertEquals(1, heroeNeo.contadores.size)
    }

    @Test
    fun `no se puede elimina condicion a contador de heroeNeo`() {
        try {
            heroeNeo.eliminarCondicion(condicion2)
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "La condición no se encuentra en el heroe.")
        }
    }

    @Test
    fun `se agregan poderes a heroeNeo`() {
        Assertions.assertEquals(0, heroeNeo.poderes.size)

        heroeNeo.agregarPoder(poderNeo1)
        heroeNeo.agregarPoder(poderNeo2)

        Assertions.assertEquals(2, heroeNeo.poderes.size)
    }
}