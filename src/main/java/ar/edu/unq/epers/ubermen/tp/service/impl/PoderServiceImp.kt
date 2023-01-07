package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.persistence.PoderDAO
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.PoderNeo
import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.persistence.PoderMutacionDAO
import ar.edu.unq.epers.ubermen.tp.service.PoderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class PoderServiceImp (
    @Autowired
    private val poderDAO: PoderDAO,
    @Autowired
    private val poderMutacionDAO: PoderMutacionDAO
) : PoderService {

    override fun crear(poder: Poder): Poder {
        this.validarNombre(poder)
        val poderNuevo = poderDAO.save(poder)
        val poderNeo = PoderNeo().fromPoder(poderNuevo)
        poderMutacionDAO.save(poderNeo)
        return poderNuevo
    }

    override fun actualizar(poder: Poder): Poder {
        return this.crear(poder)
    }

    private fun validarNombre(poder: Poder) {
        if(poder.nombre == "") {
            throw StringVacioException()
        }
    }

    private fun validarAtributo(poder: Poder) {
        if(poder.atributoPotenciado == null) {
            throw AtributoVacioException()
        }
    }

    override fun recuperar(poderId: Int): Poder {
        val poder = poderDAO.findByIdOrNull(poderId.toLong())
        if (poder == null) {
            throw RuntimeException("El id no existe.")
        }
        return poder
    }

    override fun eliminar(poderId: Int) {
        this.recuperar(poderId)
        poderDAO.deleteById(poderId.toLong())
        poderMutacionDAO.deleteById(poderId.toLong())
    }

    override fun recuperarTodos(): List<Poder> {
        return poderDAO.findAllByOrderByNombreAsc()
    }

    override fun clear() {
        poderDAO.deleteAll()
        poderMutacionDAO.detachDelete()
    }
}