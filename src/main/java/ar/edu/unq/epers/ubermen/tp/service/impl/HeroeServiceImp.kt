package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeDAO
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo
import ar.edu.unq.epers.ubermen.tp.modelo.HeroeNeo
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.service.*
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.SinPoderException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.VidaErroneaException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class HeroeServiceImp(
    @Autowired
    private val heroeDao: HeroeDAO,
    private val conflictoService : ConflictoService,
    @Autowired
    private val heroeNeoService : HeroeNeoService,
    @Autowired
    private val heroeMongoService: HeroeMongoService
    ) : HeroeService {

    override fun crear(heroe: Heroe): Heroe {
        this.validar(heroe)
        val heroeNuevo = heroeDao.save(heroe)
        val heroeNeo = HeroeNeo().fromHeroe(heroeNuevo)
        heroeNeoService.crear(heroeNeo)
        val heroeMongo = HeroeMongo(heroeNuevo.id!!)
        heroeMongoService.crear(heroeMongo)
        return heroeNuevo
    }

    private fun validar(heroe: Heroe) {
        this.validarVida(heroe)
        this.validarPoder(heroe)
        this.validarNombre(heroe)
    }

    private fun validarNombre(heroe: Heroe) {
        if(heroe.nombre == "") {
            throw StringVacioException()
        }
    }

    private fun validarVida(heroe: Heroe) {
        if(heroe.vida > 100 || heroe.vida < 0) {
            throw VidaErroneaException()
        }
    }

    private fun validarPoder(heroe: Heroe) {
        if(heroe.poderes.isEmpty()) {
            throw SinPoderException()
        }
    }

    override fun actualizar(heroe: Heroe): Heroe {
        return this.crear(heroe)    }

    override fun recuperar(heroeId:Int): Heroe {
        val heroe = heroeDao.findByIdOrNull(heroeId.toLong())
        if (heroe == null) {
            throw RuntimeException("El id [${heroeId}] no existe.")
        }
        return heroe
    }

    override fun eliminar(heroeId:Int) {
        this.recuperar(heroeId)
        heroeDao.deleteById(heroeId.toLong())
        heroeNeoService.eliminar(heroeId)
        heroeMongoService.eliminar(heroeId.toLong())
    }

    override fun recuperarTodos():List<Heroe> {
        return heroeDao.findAllByOrderByNombreAsc()
    }

    override fun resolverConflicto(heroeID:Int, conflictoID: Int) : Heroe {
        val heroe = this.recuperar(heroeID)
        val conflicto = conflictoService.recuperar(conflictoID)
        conflicto!!.resueltoPor = heroe
        conflictoService.actualizar(conflicto)
        heroe.agregarConflicto(conflicto)
        this.actualizar(heroe)
        return heroe
    }

    override fun clear() {
        heroeDao.deleteAll()
        heroeNeoService.clear()
        heroeMongoService.clear()
    }

    override fun losMasPoderosos(direccion: Direccion, pagina: Int?): List<Heroe> {
        var heroesOrdenados: List<Heroe> = emptyList()
        var siguientePagina: Int = if (pagina != null) pagina else 0

        if (direccion == Direccion.ASCENDENTE) {
            var sort = Sort.by(Sort.Direction.ASC, "poderes.size").and(
                Sort.by(Sort.Direction.ASC, "nombre")
            )
            heroesOrdenados = heroeDao.losMasPoderosos(PageRequest.of(siguientePagina,5, sort))
        } else {
            var sort = Sort.by(Sort.Direction.DESC, "poderes.size").and(
                Sort.by(Sort.Direction.ASC, "nombre")
            )
            heroesOrdenados = heroeDao.losMasPoderosos(PageRequest.of(siguientePagina,5, sort))
        }
        return heroesOrdenados
    }

    override fun guardianes(direccion: Direccion, pagina: Int?): List<Heroe> {
        var heroesOrdenados: List<Heroe> = emptyList()
        var siguientePagina: Int = if (pagina != null) pagina else 0

        if (direccion == Direccion.ASCENDENTE) {
            var sort = Sort.by(Sort.Direction.ASC, "conflictosResueltos.size").and(
                Sort.by(Sort.Direction.ASC, "nombre")
            )
            heroesOrdenados = heroeDao.guardianes(PageRequest.of(siguientePagina,5, sort))
        } else {
            var sort = Sort.by(Sort.Direction.DESC, "conflictosResueltos.size").and(
                Sort.by(Sort.Direction.ASC, "nombre")
            )
            heroesOrdenados = heroeDao.guardianes(PageRequest.of(siguientePagina,5, sort))
        }
        return heroesOrdenados
    }
}