package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PoderDAO : CrudRepository<Poder, Long> {

    fun findAllByOrderByNombreAsc() : List<Poder>
}