package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Poder

interface PoderService {
    fun crear(poder:Poder): Poder
    fun actualizar(poder:Poder):Poder
    fun recuperar(poderId:Int):Poder
    fun eliminar(poderId:Int)
    fun recuperarTodos():List<Poder>
    fun clear()
}