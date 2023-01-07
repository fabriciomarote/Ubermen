package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe

interface HeroeService {
    fun crear(heroe:Heroe):Heroe
    fun actualizar(heroe:Heroe):Heroe
    fun recuperar(heroeId:Int):Heroe?
    fun eliminar(heroeId:Int)
    fun recuperarTodos():List<Heroe>
    fun resolverConflicto(heroeID:Int, conflictoID: Int) : Heroe
    fun losMasPoderosos(direccion: Direccion, pagina: Int?): List<Heroe>
    fun guardianes(direccion: Direccion, pagina: Int?): List<Heroe>
    fun clear()

}