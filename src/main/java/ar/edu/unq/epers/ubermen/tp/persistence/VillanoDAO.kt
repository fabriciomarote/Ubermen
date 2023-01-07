package ar.edu.unq.epers.ubermen.tp.persistence

import ar.edu.unq.epers.ubermen.tp.modelo.Villano
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VillanoDAO : CrudRepository<Villano, Long> {

    fun findAllByOrderByNombreAsc() : List<Villano>

    @Query(
        value="SELECt v.nombre, v.id, v.imagenURL, v.vida FROM villano v" +
                "INNER JOIN (SELECT c.creadoPor FROM conflicto c)" +
                "INNER JOIN heroe h ON h.id = c.resueltoPor.id" +
                "INNER JOIN poderes ps"+
                "GROUP BY c.id, h.id, p.atributoPotenciado " +
                "HAVING SUM(p.cantidadPotenciada) >= 10) c " +
                "ON v.id=c.creadoPor.id " +
                "GROUP BY v.id " +
                "ORDER BY COUNT(*) ASC",
        countQuery = "select count(*) from (villano v inner join (select c.creadoPor from conflicto c inner join heroe h on h.id=c.resueltoPor inner join poderes ps group by c.id, h.id, p.atributoPotenciado HAVING SUM(p.cantidadPotenciada) >= 10) c on v.id=c.creadoPor.id group by v.id)",
        nativeQuery = true
    )
    fun hyperVillanosASC(pagina: Pageable) : List<Villano>

    @Query(
        value="SELECt v.nombre, v.id, v.imagenURL, v.vida FROM villano v" +
                "INNER JOIN (SELECT c.creadoPor FROM conflicto c)" +
                "INNER JOIN heroe h ON h.id = c.resueltoPor.id" +
                "INNER JOIN poderes ps"+
                "GROUP BY c.id, h.id, p.atributoPotenciado " +
                "HAVING SUM(p.cantidadPotenciada) >= 10) c " +
                "ON v.id=c.creadoPor.id " +
                "GROUP BY v.id " +
                "ORDER BY COUNT(*) DESC",
        countQuery = "select count(*) from (villano v inner join (select c.creadoPor from conflicto c inner join heroe h on h.id=c.resueltoPor inner join poderes ps group by c.id, h.id, p.atributoPotenciado HAVING SUM(p.cantidadPotenciada) >= 10) c on v.id=c.creadoPor.id group by v.id)",
        nativeQuery = true
    )
    fun hyperVillanosDESC(pagina: Pageable) : List<Villano>

}