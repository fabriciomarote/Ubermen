package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.modelo.Villano

interface VillanoService {
    fun crear(villano: Villano):Villano
    fun actualizar(villano:Villano):Villano
    fun recuperar(villanoId:Int):Villano
    fun eliminar(villanoId:Int)
    fun recuperarTodos():List<Villano>
    fun hyperVillanos(direccion: Direccion, pagina: Int?): List<Villano>
    fun clear()
}