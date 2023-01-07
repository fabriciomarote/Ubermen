package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PruebaDeHabilidadDAO : CrudRepository<PruebaDeHabilidad, Long> {

    fun findAllByOrderByNombreAsc() : List<PruebaDeHabilidad>
}