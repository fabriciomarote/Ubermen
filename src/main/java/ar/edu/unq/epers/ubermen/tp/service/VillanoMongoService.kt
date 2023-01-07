package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
import ar.edu.unq.epers.ubermen.tp.modelo.VillanoMongo

interface VillanoMongoService {

    fun crear(villano: VillanoMongo): VillanoMongo
    fun actualizar(villano: VillanoMongo): VillanoMongo
    fun recuperar(villanoId:Long): VillanoMongo
    fun eliminar(villanoId:Long)
    fun recuperarTodos():List<VillanoMongo>
    fun clear()
}