package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto

interface ConflictoService {
    fun crear(conflicto:Conflicto): Conflicto
    fun actualizar(conflicto:Conflicto):Conflicto
    fun recuperar(conflictoId:Int):Conflicto?
    fun eliminar(conflictoId:Int)
    fun recuperarTodos():List<Conflicto>
    fun crearConflicto(villanoID:Int, nombre:String) : Conflicto
    fun clear()
}