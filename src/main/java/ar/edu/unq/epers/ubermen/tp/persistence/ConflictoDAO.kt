package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConflictoDAO : CrudRepository<Conflicto, Long> {

    fun findAllByOrderByNombreAsc() : List<Conflicto>
}