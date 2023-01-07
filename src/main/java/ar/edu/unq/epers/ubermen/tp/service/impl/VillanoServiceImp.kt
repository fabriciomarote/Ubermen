package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.persistence.VillanoDAO
import ar.edu.unq.epers.ubermen.tp.service.VillanoMongoService
import ar.edu.unq.epers.ubermen.tp.service.VillanoService
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.SinPoderException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.VidaErroneaException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class VillanoServiceImp (
    @Autowired
    private val villanoDao: VillanoDAO,
    @Autowired
    private val villanoMongoService: VillanoMongoService
) : VillanoService {

    override fun crear(villano: Villano): Villano {
        this.validar(villano)
        val villanoNuevo = villanoDao.save(villano)
        val villanoMongo = VillanoMongo(villanoNuevo.id!!)
        villanoMongoService.crear(villanoMongo)
        return villanoNuevo
    }

    private fun validar(villano: Villano) {
        this.validarVida(villano)
        this.validarPoder(villano)
        this.validarNombre(villano)
    }

    private fun validarNombre(villano: Villano) {
        if(villano.nombre == "") {
            throw StringVacioException()
        }
    }

    private fun validarVida(villano: Villano) {
        if(villano.vida > 100 || villano.vida < 0) {
            throw VidaErroneaException()
        }
    }

    private fun validarPoder(villano: Villano) {
        if(villano.poderes.isEmpty()) {
            throw SinPoderException()
        }
    }

    override fun actualizar(villano: Villano): Villano {
        this.validar(villano)
        return this.villanoDao.save(villano)
    }

    override fun recuperar(villanoId:Int): Villano {
        val villano = villanoDao.findByIdOrNull(villanoId.toLong())
        if (villano == null) {
            throw RuntimeException("El id [${villanoId}] no existe.")
        }
        return villano
    }

    override fun eliminar(villanoId:Int) {
        this.recuperar(villanoId)
        villanoDao.deleteById(villanoId.toLong())
        villanoMongoService.eliminar(villanoId.toLong())
    }

    override fun recuperarTodos():List<Villano> {
        return villanoDao.findAllByOrderByNombreAsc()
    }

    override fun hyperVillanos(direccion: Direccion, pagina: Int?): List<Villano> {
        var villanosOrdenados: List<Villano> = emptyList()

        if (direccion == Direccion.ASCENDENTE) {
            var villanosObtenidos = this.recuperarTodos()
            villanosOrdenados = villanosObtenidos.sortedBy {
                    villano -> villano.conflictosComenzados.filter {
                    conflicto -> conflicto.resueltoPor != null
                    && this.tieneAtributoMayorA10(conflicto.resueltoPor!!)
            }.size
            }
            return villanosOrdenados.subList(pagina!! * 5, (pagina!! * 5) + 5)
        } else {
            var villanosObtenidos = this.recuperarTodos()
            villanosOrdenados = villanosObtenidos.sortedByDescending {
                    villano -> villano.conflictosComenzados.filter {
                    conflicto -> conflicto.resueltoPor != null
                    && this.tieneAtributoMayorA10(conflicto.resueltoPor!!)
            }.size
            }
            return villanosOrdenados.subList(pagina!! * 5, (pagina!! * 5) + 5)
        }
    }

    private fun tieneAtributoMayorA10(heroe : Heroe) : Boolean {
        return (heroe.getConstitucion() > 10 ||
                heroe.getDestreza() > 10 ||
                heroe.getFuerza() > 10 ||
                heroe.getInteligencia() > 10)
    }

    override fun clear() {
        villanoDao.deleteAll()
        villanoMongoService.clear()
    }
}