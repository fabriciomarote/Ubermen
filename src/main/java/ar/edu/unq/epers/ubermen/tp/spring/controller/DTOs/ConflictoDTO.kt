package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.modelo.Villano
import ar.edu.unq.epers.ubermen.tp.persistence.ConflictoDAO
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeDAO
import ar.edu.unq.epers.ubermen.tp.service.impl.ConflictoServiceImp
import ar.edu.unq.epers.ubermen.tp.service.impl.HeroeServiceImp
import org.springframework.beans.factory.annotation.Autowired

class ConflictoDTO (
    val id: Long?,
    val nombre:String?,
    val progresoHaciaSuResolucion:Int?,
    val poderesFavorables: List<PoderDTO>?,
    val pruebas: List<PruebaDeHabilidadDTO>?,
    val resueltoPor: MiniHeroeDTO?){

    companion object {
        fun desdeModelo(conflicto: Conflicto) =
            ConflictoDTO(
                id = conflicto.id,
                nombre = conflicto.nombre,
                progresoHaciaSuResolucion = conflicto.progresoHaciaSuResolucion,
                poderesFavorables = conflicto.poderesFavorables
                    .map {poder -> PoderDTO.desdeModelo(poder)}
                    .toCollection(HashSet()).toList(),
                pruebas = conflicto.pruebas
                    .map { prueba -> PruebaDeHabilidadDTO.desdeModelo(prueba) }
                    .toCollection(HashSet()).toList(),
                resueltoPor = MiniHeroeDTO(conflicto.resueltoPor!!.id, conflicto.resueltoPor!!.nombre)
            )
    }

    fun aModelo() : Conflicto {
        val conflicto = Conflicto()
        conflicto.id = this.id
        conflicto.nombre = this.nombre
        conflicto.progresoHaciaSuResolucion = this.progresoHaciaSuResolucion!!
        conflicto.poderesFavorables = this.poderesFavorables
            ?.map { PoderDTO  -> PoderDTO.aModelo()}?.
            toCollection(HashSet())
            ?: HashSet()
        conflicto.pruebas = this.pruebas
            ?.map { PruebaDeHabilidadDTO  -> PruebaDeHabilidadDTO.aModelo()}?.
            toCollection(HashSet())
            ?: HashSet()
        return conflicto
    }

    fun aModelo(heroeService : HeroeServiceImp): Conflicto {
        val conflicto = Conflicto()
        conflicto.id = this.id
        conflicto.nombre = this.nombre
        conflicto.progresoHaciaSuResolucion = this.progresoHaciaSuResolucion!!
        conflicto.poderesFavorables = this.poderesFavorables
            ?.map { PoderDTO  -> PoderDTO.aModelo()}?.
            toCollection(HashSet())
            ?: HashSet()
        conflicto.pruebas = this.pruebas
            ?.map { PruebaDeHabilidadDTO  -> PruebaDeHabilidadDTO.aModelo()}?.
            toCollection(HashSet())
            ?: HashSet()
        conflicto.resueltoPor = this.resueltoPor!!.aModelo(heroeService)
        return conflicto
    }

    fun aModelo(heroe: Heroe): Conflicto {
        val conflicto = aModelo()
        conflicto.resueltoPor = heroe
        return conflicto
    }

    fun aModelo(villano: Villano): Conflicto {
        val conflicto = aModelo()
        conflicto.creadoPor = villano
        return conflicto
    }
}