package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.VillanoMongo
import ar.edu.unq.epers.ubermen.tp.persistence.VillanoCoordenadaDAO
import ar.edu.unq.epers.ubermen.tp.service.VillanoMongoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class VillanoMongoServiceImp(
        @Autowired
        private val villanoCoordenadaDAO: VillanoCoordenadaDAO
        ) : VillanoMongoService {

    override fun crear(villano: VillanoMongo): VillanoMongo {
        return villanoCoordenadaDAO.save(villano)
    }

    override fun actualizar(villano: VillanoMongo): VillanoMongo {
        return this.crear(villano)
    }

    override fun recuperar(villanoId: Long): VillanoMongo {
        try {
            val villanoMongo = villanoCoordenadaDAO.findByIdSQL(villanoId)
            return villanoMongo
        } catch (e: RuntimeException) {
            throw RuntimeException("El id no existe.")
        }
    }

    override fun eliminar(villanoId: Long) {
        val villanoMongo = this.recuperar(villanoId)
        villanoCoordenadaDAO.deleteById(villanoMongo.id)
    }

    override fun recuperarTodos(): List<VillanoMongo> {
        return villanoCoordenadaDAO.findAll()
    }

    override fun clear() {
        villanoCoordenadaDAO.deleteAll()
    }
}