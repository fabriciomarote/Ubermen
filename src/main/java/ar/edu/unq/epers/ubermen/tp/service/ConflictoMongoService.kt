package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto
import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo

interface ConflictoMongoService {

    fun crear(conflicto: ConflictoMongo): ConflictoMongo
    fun actualizar(conflicto: ConflictoMongo): ConflictoMongo
    fun recuperar(conflictoId:Long): ConflictoMongo
    fun eliminar(conflictoId:Long)
    fun recuperarTodos():List<ConflictoMongo>
    fun obtenerConflictoAleatorio(heroe : HeroeMongo) : ConflictoMongo
    fun masPicante() : String
    fun clear()
}