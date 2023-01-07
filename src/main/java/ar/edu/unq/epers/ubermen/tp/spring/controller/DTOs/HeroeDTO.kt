package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Heroe

class HeroeDTO (
    val id: Long?,
    val nombre:String?,
    val vida:Int = 1,
    val imagenUrl:String?,
    val poderes: List<PoderDTO>?,
    val conflictosResueltos: List<ConflictoDTO>?,
    val constitucion : Int = 1,
    val fuerza : Int = 1,
    val inteligencia : Int = 1,
    val destreza : Int = 1) {


    companion object {
        fun desdeModelo(heroe: Heroe) =
            HeroeDTO(
                id = heroe.id,
                nombre = heroe.nombre,
                vida = heroe.vida,
                imagenUrl = heroe.imagenURL,
                poderes = heroe.poderes
                    .map{ p -> PoderDTO.desdeModelo(p) }
                    .toCollection(HashSet()).toList(),
                conflictosResueltos = heroe.conflictosResueltos
                    .map{ c -> ConflictoDTO.desdeModelo(c) }
                    .toCollection(HashSet()).toList(),
                constitucion = heroe.getConstitucion(),
                fuerza = heroe.getFuerza(),
                inteligencia = heroe.getInteligencia(),
                destreza = heroe.getDestreza()
            )
    }

    fun aModelo(): Heroe {
        val heroe = Heroe()
        heroe.id = this.id
        heroe.cambiarNombre(this.nombre!!)
        heroe.cambiarVida(this.vida)
        heroe.cambiarImagen(this.imagenUrl!!)
        heroe.poderes = this.poderes
            ?.map { poderDTO  -> poderDTO.aModelo() }?.
            toCollection(HashSet())
            ?: HashSet()
        heroe.conflictosResueltos = this.conflictosResueltos
            ?.map { conflictoDTO  -> conflictoDTO.aModelo(heroe) }?.
            toCollection(HashSet())
            ?: HashSet()
        return heroe
    }
}
