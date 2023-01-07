package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.HeroeMongo
import ar.edu.unq.epers.ubermen.tp.persistence.ConflictoCoordenadaDAO
import ar.edu.unq.epers.ubermen.tp.persistence.DistritoDAO
import ar.edu.unq.epers.ubermen.tp.persistence.HeroeCoordenadaDAO
import ar.edu.unq.epers.ubermen.tp.service.impl.ConflictoMongoServiceImp
import ar.edu.unq.epers.ubermen.tp.service.impl.DistritoServiceImp
import ar.edu.unq.epers.ubermen.tp.service.impl.HeroeMongoServiceImp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

@SpringBootTest
class HeroeMongoServiceTest {

    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    lateinit var heroeMongoService: HeroeMongoService

    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    lateinit var conflictoMongoService : ConflictoMongoService

    @Autowired
    lateinit var distritoDAO : DistritoDAO
    lateinit var distritoService: DistritoService

    lateinit var batmanMongo : HeroeMongo
    lateinit var coordenadaBatman : GeoJsonPoint

    lateinit var supermanMongo : HeroeMongo
    lateinit var coordenadaSuperman : GeoJsonPoint

    lateinit var spidermanMongo : HeroeMongo
    lateinit var coordenadaSpiderman : GeoJsonPoint

    @BeforeEach
    fun prepare() {
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)

        coordenadaBatman = GeoJsonPoint(-34.7711191,-58.2285175)
        batmanMongo = HeroeMongo(1, coordenadaBatman, "1")

        coordenadaSuperman = GeoJsonPoint(-34.74236447331491, -58.22010277745575)
        supermanMongo = HeroeMongo(2, coordenadaSuperman, "1")

        coordenadaSpiderman = GeoJsonPoint(-34.7682929,-58.2301455)
        spidermanMongo = HeroeMongo(3, coordenadaSpiderman, "1")
    }

    @Test
    fun `se crean 3 heroes y se recuperan uno por uno y luego todos`() {
        // Se crea y recupera batman
        heroeMongoService.crear(batmanMongo)
        var batmanRecuperado = heroeMongoService.recuperar(1)
        Assertions.assertEquals(batmanRecuperado, batmanMongo)

        var heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(1, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(batmanMongo))

        // Se crea y recupera superman
        // el recuperarTodos() además contiene a batman
        heroeMongoService.crear(supermanMongo)
        var supermanRecuperado = heroeMongoService.recuperar(2)
        Assertions.assertEquals(supermanRecuperado, supermanMongo)
        heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(2, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(batmanMongo))
        Assertions.assertTrue(heroesRecuperados.contains(supermanMongo))

        // Se crea y recupera Spiderman
        // el recuperarTodos() además contiene al Batman y Superman
        heroeMongoService.crear(spidermanMongo)
        var spidermanRecuperado = heroeMongoService.recuperar(3)
        Assertions.assertEquals(spidermanRecuperado, spidermanMongo)
        heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(3, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(batmanMongo))
        Assertions.assertTrue(heroesRecuperados.contains(supermanMongo))
        Assertions.assertTrue(heroesRecuperados.contains(spidermanMongo))
    }

    @Test
    fun `se actualiza un heroe tiene otra coordenada`() {
        // Se crea y recupera batman
        heroeMongoService.crear(batmanMongo)
        var batmanRecuperado = heroeMongoService.recuperar(1)
        Assertions.assertEquals(batmanRecuperado, batmanMongo)

        var heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(1, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(batmanMongo))

        // Batman cambia de coordenada
        var coordenada2Batman = GeoJsonPoint(-34.7682929,-58.2301455)
        batmanMongo.coordenada = coordenada2Batman
        heroeMongoService.actualizar(batmanMongo)

        // Se observa que el conflicto está resuelto y persistido
        batmanRecuperado = heroeMongoService.recuperar(1)
        Assertions.assertEquals(batmanRecuperado, batmanMongo)
    }

    @Test
    fun `se crea a Batman y se elimina`() {
        // Se crea y recupera batman
        heroeMongoService.crear(batmanMongo)
        var batmanRecuperado = heroeMongoService.recuperar(1)
        Assertions.assertEquals(batmanRecuperado, batmanMongo)

        var heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(1, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(batmanMongo))

        // Se elimina a Batman y se observa que el recuperar todos es vacio
        heroeMongoService.eliminar(1)
        heroesRecuperados = heroeMongoService.recuperarTodos()
        Assertions.assertEquals(0, heroesRecuperados.size)
    }

    @Test
    fun `no se puede recuperar el heroe con id 5 porque no existe`() {
        val id = 5
        try {
            heroeMongoService.recuperar(id.toLong())
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `no se puede eliminar el heroe con id 10 porque no existe`() {
        val id = 10
        try {
            heroeMongoService.eliminar(id.toLong())
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @AfterEach
    fun clear() {
        heroeMongoService.clear()
    }
}