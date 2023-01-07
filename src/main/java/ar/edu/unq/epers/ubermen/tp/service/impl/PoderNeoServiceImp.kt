package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.PoderNeo
import ar.edu.unq.epers.ubermen.tp.persistence.PoderMutacionDAO
import ar.edu.unq.epers.ubermen.tp.service.PoderNeoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class PoderNeoServiceImp(
    @Autowired
    private val poderMutacionDAO: PoderMutacionDAO
    ) : PoderNeoService {

    override fun actualizar(poderNeo: PoderNeo) {
        poderMutacionDAO.save(poderNeo)
    }

    override fun recuperar(poderNeoId: Int): PoderNeo {
        val poderNeo = poderMutacionDAO.findByIdOrNull(poderNeoId.toLong())
        if (poderNeo == null) {
            throw RuntimeException("El id no existe.")
        }
        return poderNeo
    }

    override fun poderesQuePuedenMutar(idHeroe: Long) : Set<PoderNeo>{
        return poderMutacionDAO.poderesQuePuedenMutar(idHeroe)
    }

    override fun mutacionesHabilitadas(idHeroe: Long, idPoder: Long): Set<PoderNeo> {
        return poderMutacionDAO.mutacionesHabilitadas(idHeroe, idPoder)
    }

    override fun caminoMasRentable(idPoder1: Long, idPoder2: Long, atributos: Set<Atributo>): List<PoderNeo> {
        return poderMutacionDAO.caminoMasRentable(idPoder1, idPoder2, atributos)
    }

    override fun eliminar() {
        poderMutacionDAO.detachDelete()
    }
}