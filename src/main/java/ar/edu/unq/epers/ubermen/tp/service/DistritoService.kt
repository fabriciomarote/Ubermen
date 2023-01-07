package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
import ar.edu.unq.epers.ubermen.tp.modelo.Distrito
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

interface DistritoService {

    fun crear(distrito:Distrito): Distrito
    fun actualizar(distrito: Distrito): Distrito
    fun masPicante(): Distrito
    fun masVigilado(): Distrito
    fun recuperar(idDistrito: String): Distrito
    fun eliminar(idDistrito: String)
    fun recuperarTodos() : List<Distrito>
    fun distritoDe(coordenada : GeoJsonPoint) : String
    fun clear()
}