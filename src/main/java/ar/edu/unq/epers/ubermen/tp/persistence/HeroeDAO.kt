package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Direccion
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HeroeDAO : CrudRepository<Heroe, Long> {

    fun findAllByOrderByNombreAsc() : List<Heroe>

    @Query("FROM Heroe h")
    fun losMasPoderosos(pagina: PageRequest): List<Heroe>

    @Query("FROM Heroe h")
    fun guardianes(pagina: PageRequest): List<Heroe>
}