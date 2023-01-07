package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PoderNeoTest {

    lateinit var condicion1: Condicion
    lateinit var condicion2: Condicion
    lateinit var condicion3: Condicion
    lateinit var poderNeo: PoderNeo

    @BeforeEach
    fun prepare() {

        var pruebasDeHabilidad = Evaluacion.PRUEBASDEHABILIDAD
        var exitosDeTiradas = Evaluacion.EXITOSDETIRADAS
        var fuerza = Atributo.FUERZA
        var destreza = Atributo.DESTREZA
        var superadas = Resultado.SUPERADAS
        var falladas = Resultado.FALLADAS
        var acumulados = Resultado.ACUMULADOS

        poderNeo = PoderNeo(10, "SuperFuerza")
        condicion1 = Condicion(2, pruebasDeHabilidad, fuerza, superadas)
        condicion2 = Condicion(2, pruebasDeHabilidad, destreza, falladas)
        condicion3 = Condicion(3, exitosDeTiradas, fuerza, acumulados)

        poderNeo.agregarCondicion(condicion1)
    }

    @Test
    fun constructorTest() {
        Assertions.assertEquals(10, poderNeo.id)
        Assertions.assertEquals("SuperFuerza", poderNeo.nombre)
        Assertions.assertEquals(1, poderNeo.condiciones.size)
    }

    @Test
    fun `se agregan condiciones a contador de heroeNeo`() {
        Assertions.assertEquals(1, poderNeo.condiciones.size)

        poderNeo.agregarCondicion(condicion2)
        poderNeo.agregarCondicion(condicion3)

        Assertions.assertEquals(3, poderNeo.condiciones.size)
    }
}