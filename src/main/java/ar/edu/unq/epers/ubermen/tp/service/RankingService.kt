package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.modelo.Villano

interface RankingService {

    fun losMasPoderosos(direccion: Direccion, pagina: Int?): List<Heroe>

    fun guardianes(direccion: Direccion, pagina: Int?): List<Heroe>

    fun hyperVillanos(direccion: Direccion, pagina: Int?): List<Villano>

}