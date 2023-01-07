package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo

interface HeroeMongoService {
    fun crear(heroe: HeroeMongo): HeroeMongo
    fun actualizar(heroe: HeroeMongo): HeroeMongo
    fun recuperar(heroeId:Long): HeroeMongo
    fun eliminar(heroeId:Long)
    fun recuperarTodos():List<HeroeMongo>
    fun masVigilado() : String
    fun clear()
}