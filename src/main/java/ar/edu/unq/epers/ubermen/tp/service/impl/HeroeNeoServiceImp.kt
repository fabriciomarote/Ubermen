package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeNeo
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeMutacionDAO
import ar.edu.unq.epers.ubermen.tp.service.HeroeNeoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class HeroeNeoServiceImp(
    @Autowired
    private val heroeMutacionDAO : HeroeMutacionDAO
    ) : HeroeNeoService {

    override fun crear(heroeNeo: HeroeNeo) {
        heroeMutacionDAO.save(heroeNeo)
    }

    override fun actualizar(heroeNeo: HeroeNeo) {
        this.crear(heroeNeo)
    }

    override fun recuperar(heroeNeoId: Int): HeroeNeo {
        val heroeNeo = heroeMutacionDAO.findByIdOrNull(heroeNeoId.toLong())
        if (heroeNeo == null) {
            throw RuntimeException("El id no existe.")
        }
        return heroeNeo
    }

    override fun eliminar(heroeId: Int) {
        val heroeNeo = this.recuperar(heroeId)
        heroeMutacionDAO.deleteById(heroeNeo.id)
    }

    override fun clear() {
        heroeMutacionDAO.detachDelete()
    }
}