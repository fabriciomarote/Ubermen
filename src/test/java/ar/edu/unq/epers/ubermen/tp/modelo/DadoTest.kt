package ar.edu.unq.epers.ubermen.tp.modelo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DadoTest {
    lateinit var dado : Dado

    @BeforeEach
    fun prepare() {
        dado = Dado()
    }

    @Test
    fun tirarDadoTest() {
        val valorDado = dado.tirarDado()
        val rango = 1..6

        Assertions.assertTrue(rango.contains(valorDado))
    }
}