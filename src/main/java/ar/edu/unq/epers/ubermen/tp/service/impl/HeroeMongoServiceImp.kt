package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeCoordenadaDAO
import ar.edu.unq.epers.ubermen.tp.service.HeroeMongoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class HeroeMongoServiceImp(
        @Autowired
        private val heroeCoordenadaDAO: HeroeCoordenadaDAO
        ) : HeroeMongoService {

    override fun crear(heroe: HeroeMongo): HeroeMongo {
        return heroeCoordenadaDAO.save(heroe)
    }

    override fun actualizar(heroe: HeroeMongo): HeroeMongo {
        return this.crear(heroe)
    }

    override fun recuperar(heroeId: Long): HeroeMongo {
        try {
            val heroeMongo = heroeCoordenadaDAO.findByIdSQL(heroeId)
            return heroeMongo
        } catch (e: RuntimeException) {
            throw RuntimeException("El id no existe.")
        }
    }

    override fun eliminar(heroeId: Long) {
        val heroeMongo = this.recuperar(heroeId)
        heroeCoordenadaDAO.deleteById(heroeMongo.id)
    }

    override fun recuperarTodos(): List<HeroeMongo> {
        return heroeCoordenadaDAO.findAll()
    }

    override fun masVigilado(): String {
        var lista = heroeCoordenadaDAO.masVigilado()
        return lista.first().idDistrito!!
    }

    override fun clear() {
        heroeCoordenadaDAO.deleteAll()
    }
}