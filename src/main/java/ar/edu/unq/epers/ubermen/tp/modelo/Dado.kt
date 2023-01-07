package ar.edu.unq.epers.ubermen.tp.modelo

import kotlin.random.Random
import kotlin.random.nextInt

class Dado {
    private val rango = 1..6

    fun tirarDado(): Int{
        return Random.nextInt(rango)
    }
}