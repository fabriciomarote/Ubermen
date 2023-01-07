package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.persistence.ConflictoDAO
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeDAO
import ar.edu.unq.epers.ubermen.tp.service.PruebaDeHabilidadService
import ar.edu.unq.epers.ubermen.tp.service.VillanoService
import ar.edu.unq.epers.ubermen.tp.service.impl.ConflictoServiceImp
import ar.edu.unq.epers.ubermen.tp.service.impl.HeroeServiceImp
import org.springframework.beans.factory.annotation.Autowired

class MiniHeroeDTO (
    var id: Long?,
    var nombre:String?) {

    companion object {
        fun desdeModelo(heroe: Heroe) =
            MiniHeroeDTO(
                id = heroe.id,
                nombre = heroe.nombre
            )
    }

    fun aModelo(heroeService : HeroeServiceImp) : Heroe {
        return heroeService.recuperar(this.id!!.toInt())
    }
}