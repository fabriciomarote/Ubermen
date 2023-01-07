package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad

interface PruebaDeHabilidadService {
    fun crear(pruebaDeHabilidad:PruebaDeHabilidad): PruebaDeHabilidad
    fun actualizar(pruebaDeHabilidad:PruebaDeHabilidad):PruebaDeHabilidad
    fun recuperar(pruebaDeHabilidadId:Int):PruebaDeHabilidad
    fun eliminar(pruebaDeHabilidadId:Int)
    fun recuperarTodos():List<PruebaDeHabilidad>
    fun clear()
}