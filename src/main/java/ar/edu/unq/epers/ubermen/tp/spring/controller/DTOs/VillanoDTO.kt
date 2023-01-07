package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Villano


class VillanoDTO (
    val id: Long?,
    val nombre:String?,
    val vida:Int = 1,
    val imagenUrl:String?,
    val poderes: List<PoderDTO>?,
    val conflictosComenzados: List<ConflictoDTO>?,
    val constitucion : Int = 1,
    val fuerza : Int = 1,
    val inteligencia : Int = 1,
    val destreza : Int = 1) {

    companion object {
        fun desdeModelo(villano: Villano) =
            VillanoDTO(
                id = villano.id,
                nombre = villano.nombre,
                vida = villano.vida,
                imagenUrl = villano.imagenURL,
                poderes = villano.poderes
                    .map{ p -> PoderDTO.desdeModelo(p) }
                    .toCollection(HashSet()).toList(),
                conflictosComenzados = villano.conflictosComenzados
                    .map{ c -> ConflictoDTO.desdeModelo(c) }
                    .toCollection(HashSet()).toList(),
                constitucion = villano.getConstitucion(),
                fuerza = villano.getFuerza(),
                inteligencia = villano.getInteligencia(),
                destreza = villano.getDestreza()
            )
    }

    fun aModelo(): Villano {
        val villano = Villano()
        villano.id = this.id
        villano.cambiarNombre(this.nombre!!)
        villano.cambiarVida(this.vida)
        villano.cambiarImagen(this.imagenUrl!!)
        villano.poderes = this.poderes
            ?.map { poderDTO  -> poderDTO.aModelo() }?.
            toCollection(HashSet())
            ?: HashSet()
        villano.conflictosComenzados = this.conflictosComenzados
            ?.map { conflictoDTO  -> conflictoDTO.aModelo(villano) }?.
            toCollection(HashSet())
            ?: HashSet()
        return villano
    }
}