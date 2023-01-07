package ar.edu.unq.epers.ubermen.tp.modelo

import kotlin.random.Random
import kotlin.random.nextInt

object GeneradorRandom {
    private var strategy : GeneratorStrategy = RandomStrategy

    fun valorRandom(collection: Collection<String>): String {
        return strategy.valorRandom(collection)
    }

    fun valorRandom(n: Int): Int{
        return strategy.valorRandom(n)
    }

    fun cambiarStrategy(strategy: GeneratorStrategy){
        this.strategy = strategy
    }
}

interface GeneratorStrategy {
    fun  valorRandom(collection: Collection<String>): String
    fun valorRandom(n: Int): Int
}

object RandomStrategy: GeneratorStrategy {
    override fun valorRandom(collection: Collection<String>): String {
        return collection.random()
    }

    override fun valorRandom(n: Int): Int {
        val rango = 0..n
        return Random.nextInt(rango)
    }
}

class FixedStrategy : GeneratorStrategy {
    private var fixedInt:Int = 0
    private var fixedString:String = ""

    constructor(n:Int) {
        this.fixedInt = n
    }

    constructor(string:String) {
        this.fixedString = string
    }

    override fun valorRandom(collection: Collection<String>): String {
        return fixedString
    }

    override fun valorRandom(n: Int): Int {
        return fixedInt
    }
}