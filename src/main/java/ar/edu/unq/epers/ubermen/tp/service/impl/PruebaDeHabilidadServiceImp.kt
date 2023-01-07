package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.persistence.PruebaDeHabilidadDAO
import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad
import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.service.PruebaDeHabilidadService
import ar.edu.unq.epers.ubermen.tp.service.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class PruebaDeHabilidadServiceImp(
    @Autowired
    private val pruebaDeHabilidadDAO: PruebaDeHabilidadDAO,
) : PruebaDeHabilidadService {

    override fun crear(pruebaDeHabilidad: PruebaDeHabilidad): PruebaDeHabilidad {
        this.validar(pruebaDeHabilidad)
        return pruebaDeHabilidadDAO.save(pruebaDeHabilidad)
    }

    private fun validar(pruebaDeHabilidad : PruebaDeHabilidad) {
        this.validarNombre(pruebaDeHabilidad)
        this.validarDificultad(pruebaDeHabilidad)
        this.validarRetalacion(pruebaDeHabilidad)
        this.validarAtributoDesafiado(pruebaDeHabilidad)
    }

    private fun validarNombre(pruebaDeHabilidad: PruebaDeHabilidad) {
        if(pruebaDeHabilidad.nombre == "") {
            throw StringVacioException()
        }
    }

    private fun validarDificultad(pruebaDeHabilidad: PruebaDeHabilidad) {
        if(pruebaDeHabilidad.dificultad < 0) {
            throw CantidadNegativaException()
        }
    }

    private fun validarRetalacion(pruebaDeHabilidad: PruebaDeHabilidad) {
        if(pruebaDeHabilidad.retalacion < 0) {
            throw CantidadNegativaException()
        }
    }

    private fun validarAtributoDesafiado(pruebaDeHabilidad: PruebaDeHabilidad) {
        if(pruebaDeHabilidad.atributoDesafiado == null) {
            throw AtributoVacioException()
        }
    }

    override fun actualizar(pruebaDeHabilidad: PruebaDeHabilidad): PruebaDeHabilidad {
        return this.crear(pruebaDeHabilidad)
    }

    override fun recuperar(pruebaDeHabilidadId: Int): PruebaDeHabilidad {
        val prueba = pruebaDeHabilidadDAO.findByIdOrNull(pruebaDeHabilidadId.toLong())
        if (prueba == null) {
            throw RuntimeException("La prueba de habilidad no existe.")
        }
        return prueba
    }

    override fun eliminar(pruebaDeHabilidadId: Int) {
        this.recuperar(pruebaDeHabilidadId)
        pruebaDeHabilidadDAO.deleteById(pruebaDeHabilidadId.toLong())
    }

    override fun recuperarTodos(): List<PruebaDeHabilidad> {
        return pruebaDeHabilidadDAO.findAllByOrderByNombreAsc()
    }

    override fun clear() {
        pruebaDeHabilidadDAO.deleteAll()
    }
}