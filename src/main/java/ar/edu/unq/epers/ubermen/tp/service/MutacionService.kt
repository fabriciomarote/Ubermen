package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.Condicion

interface MutacionService {
   fun crearMutacion(idPoder1: Long, idPoder2: Long, condicion: Condicion)

   fun poderesQuePuedenMutar(idHeroe: Long): Set<Poder>

   fun mutacionesHabilitadas(idHeroe:Long, idPoder: Long): Set<Poder>

   fun caminoMasRentable(idPoder1: Long, idPoder2: Long, atributos: Set<Atributo>): List<Poder>

   fun deleteAll()
}