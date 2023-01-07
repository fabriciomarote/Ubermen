package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeNeo

interface HeroeNeoService {

    fun crear(heroeNeo: HeroeNeo)
    fun actualizar(heroeNeo: HeroeNeo)
    fun recuperar(hereoNeoId: Int): HeroeNeo
    fun eliminar(heroeId: Int)
    fun clear()
}