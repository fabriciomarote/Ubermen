package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.Distrito
import ar.edu.unq.epers.ubermen.tp.persistence.DistritoDAO
import ar.edu.unq.epers.ubermen.tp.service.ConflictoMongoService
import ar.edu.unq.epers.ubermen.tp.service.DistritoService
import ar.edu.unq.epers.ubermen.tp.service.HeroeMongoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class DistritoServiceImp(
            @Autowired
            private val distritoDAO : DistritoDAO,
            private val heroeMongoService: HeroeMongoService,
            private val conflictoMongoService: ConflictoMongoService
        ) : DistritoService {

    override fun crear(distrito: Distrito): Distrito {
        return distritoDAO.save(distrito)
    }

    override fun actualizar(distrito: Distrito): Distrito {
        return this.crear(distrito)
    }

    override fun recuperar(idDistrito: String): Distrito {
        val distrito = distritoDAO.findByIdOrNull(idDistrito)
        if (distrito == null) {
            throw RuntimeException("El id no existe.")
        }
        return distrito!!
    }

    override fun eliminar(idDistrito: String) {
        distritoDAO.deleteById(idDistrito)
    }

    override fun recuperarTodos() : List<Distrito> {
        return distritoDAO.findAll()
    }

    override fun masPicante(): Distrito {
        var idDistrito = conflictoMongoService.masPicante()
        var masPicante = this.recuperar(idDistrito)
        return masPicante
    }

    override fun masVigilado(): Distrito {
        var idDistrito = heroeMongoService.masVigilado()
        var masVigilado = this.recuperar(idDistrito)
        return masVigilado
    }

    override fun distritoDe(coordenada : GeoJsonPoint) : String {
        var distrito = distritoDAO.distritoDe(coordenada.x, coordenada.y)
        return distrito.id!!
    }

    override fun clear() {
        distritoDAO.deleteAll()
    }
}