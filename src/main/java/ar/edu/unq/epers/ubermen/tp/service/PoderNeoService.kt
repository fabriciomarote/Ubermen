package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.PoderNeo

interface PoderNeoService {
    fun actualizar(poderNeo: PoderNeo)
    fun recuperar(poderNeoId: Int) : PoderNeo
    fun eliminar()
    fun poderesQuePuedenMutar(idHeroe: Long) : Set<PoderNeo>
    fun mutacionesHabilitadas(idHeroe: Long, idPoder: Long): Set<PoderNeo>
    fun caminoMasRentable(idPoder1: Long, idPoder2: Long, atributos: Set<Atributo>): List<PoderNeo>
}