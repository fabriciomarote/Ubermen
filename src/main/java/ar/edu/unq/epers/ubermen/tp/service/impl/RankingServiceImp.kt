package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.modelo.Villano
import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.service.HeroeService
import ar.edu.unq.epers.ubermen.tp.service.RankingService
import ar.edu.unq.epers.ubermen.tp.service.VillanoService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class RankingServiceImp (
    private val heroeService : HeroeService,
    private val villanoService : VillanoService
) : RankingService {

    override fun losMasPoderosos(direccion: Direccion, pagina: Int?): List<Heroe> {
        return heroeService.losMasPoderosos(direccion, pagina!!)
    }

    override fun guardianes(direccion: Direccion, pagina: Int?): List<Heroe> {
        return heroeService.guardianes(direccion, pagina!!)
    }

    override fun hyperVillanos(direccion: Direccion, pagina: Int?): List<Villano> {
        return villanoService.hyperVillanos(direccion, pagina!!)
    }


}