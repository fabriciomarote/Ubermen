package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo
import ar.edu.unq.epers.ubermen.tp.persistence.ConflictoCoordenadaDAO
import ar.edu.unq.epers.ubermen.tp.service.ConflictoMongoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class ConflictoMongoServiceImp(
        @Autowired
        private val conflictoCoordenadaDAO : ConflictoCoordenadaDAO
        ) : ConflictoMongoService {

    override fun crear(conflicto: ConflictoMongo): ConflictoMongo {
        return conflictoCoordenadaDAO.save(conflicto)
    }

    override fun actualizar(conflicto: ConflictoMongo): ConflictoMongo {
        return this.crear(conflicto)
    }

    override fun recuperar(conflictoId: Long): ConflictoMongo {
        try {
            val conflictoMongo = conflictoCoordenadaDAO.findByIdSQL(conflictoId)
            return conflictoMongo
        } catch (e: RuntimeException) {
            throw RuntimeException("El id no existe.")
        }
    }

    override fun eliminar(conflictoId: Long) {
        val conflictoMongo = this.recuperar(conflictoId)
        conflictoCoordenadaDAO.deleteById(conflictoMongo.id)
    }

    override fun recuperarTodos(): List<ConflictoMongo> {
        return conflictoCoordenadaDAO.findAll()
    }

    override fun obtenerConflictoAleatorio(heroe: HeroeMongo): ConflictoMongo {
        var lista = conflictoCoordenadaDAO.obtenerConflictoAleatorio(heroe.coordenada!!.x, heroe.coordenada!!.y)
        if(lista.isEmpty()) {
            throw RuntimeException("No hay ningún conflicto dentro de 1km de radio sin resolver.")
        }
        return lista.random()
    }

    override fun masPicante() : String {
        var lista =  conflictoCoordenadaDAO.masPicante()
        return lista.first().idDistrito!!
    }

    override fun clear() {
        conflictoCoordenadaDAO.deleteAll()
    }
}