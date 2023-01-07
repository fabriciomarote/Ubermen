package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Condicion
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MutacionServiceImp(
    @Autowired
    private val poderService: PoderService,
    private val heroeService: HeroeService,
    private val poderNeoService: PoderNeoService,
    private val heroeNeoService: HeroeNeoService
     ) : MutacionService {

    override fun crearMutacion(idPoder1: Long, idPoder2: Long, condicion: Condicion) {
        this.validarPoder(idPoder1)
        this.validarPoder(idPoder2)

        var poderNeo = poderNeoService.recuperar(idPoder1.toInt())
        var poderNeo2 = poderNeoService.recuperar(idPoder2.toInt())

        condicion.cambiarTargetNode(poderNeo2)
        poderNeo.agregarCondicion(condicion)
        poderNeoService.actualizar(poderNeo)
    }

    private fun validarPoder(idPoder: Long) {
        // Levanta excepcion si no existe el poder
        poderService.recuperar(idPoder.toInt())
        // Levanta excepcion si no existe el poderNeo
        poderNeoService.recuperar(idPoder.toInt())
    }

    override fun poderesQuePuedenMutar(idHeroe: Long): Set<Poder> {
        this.validarHeroe(idHeroe)
        var poderes = emptySet<Poder>()
        poderNeoService.poderesQuePuedenMutar(idHeroe).map { poderNeo ->
            poderes += poderService.recuperar(poderNeo.id!!.toInt())
        }
        return poderes
    }

    private fun validarHeroe(idHeroe : Long) {
        // Levanta excepcion si no existe el heroe
        heroeService.recuperar(idHeroe.toInt())
        // Levanta excepcion si no existe el heroeNeo
        heroeNeoService.recuperar(idHeroe.toInt())
    }

    override fun mutacionesHabilitadas(idHeroe: Long, idPoder: Long): Set<Poder> {
        this.validarHeroe(idHeroe)
        this.validarPoder(idPoder)
        var poderes = emptySet<Poder>()
        poderNeoService.mutacionesHabilitadas(idHeroe, idPoder).map { poderNeo ->
            poderes += poderService.recuperar(poderNeo.id!!.toInt())
        }
        return poderes
    }

    override fun caminoMasRentable(idPoder1: Long, idPoder2: Long, atributos: Set<Atributo>): List<Poder> {
        this.validarPoder(idPoder1)
        this.validarPoder(idPoder2)
        var caminoMasRentables = mutableListOf<Poder>()
        poderNeoService.caminoMasRentable(idPoder1, idPoder2, atributos).forEach { poderNeo ->
            caminoMasRentables.add(poderService.recuperar(poderNeo.id!!.toInt()))
        }
        return caminoMasRentables
    }

    override fun deleteAll() {
        heroeNeoService.clear()
        poderNeoService.eliminar()
    }
}
