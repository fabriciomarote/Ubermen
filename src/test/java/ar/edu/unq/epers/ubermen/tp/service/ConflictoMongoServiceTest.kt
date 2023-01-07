package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.ConflictoMongo
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
class ConflictoMongoServiceTest {
    @Autowired
    lateinit var conflictoCoordenadaDAO: ConflictoCoordenadaDAO
    lateinit var conflictoMongoService: ConflictoMongoService

    @Autowired
    lateinit var distritoDAO: DistritoDAO
    lateinit var distritoMongoService: DistritoService

    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    lateinit var heroeMongoService : HeroeMongoService

    lateinit var conflicto1 : ConflictoMongo
    lateinit var conflicto2 : ConflictoMongo
    lateinit var conflicto3 : ConflictoMongo

    lateinit var coordenada1 : GeoJsonPoint
    lateinit var coordenada2 : GeoJsonPoint
    lateinit var coordenada3 : GeoJsonPoint

    lateinit var heroeMongo : HeroeMongo
    lateinit var coordenadaHeroe : GeoJsonPoint

    @BeforeEach
    fun prepare() {
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.distritoMongoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)

        coordenada1 = GeoJsonPoint(-34.760951800003326, -58.20423290186426)
        conflicto1 = ConflictoMongo(1, coordenada1, "1")

        coordenada2 = GeoJsonPoint(-34.76069377068813, -58.20723584123123)
        conflicto2 = ConflictoMongo(2, coordenada2, "1")

        coordenada3 = GeoJsonPoint(-34.74236447331491, -58.22010277745575)
        conflicto3 = ConflictoMongo(3, coordenada3, "1")

        coordenadaHeroe = GeoJsonPoint(-34.75948993378682, -58.20520374176037)
        heroeMongo = HeroeMongo(1, coordenadaHeroe, "1")
    }

    @Test
    fun `se crean 3 conflictos y se recuperan uno por uno y luego todos`() {
        // Se crea y recupera el conflicto 1
        conflictoMongoService.crear(conflicto1)
        var conflicto1Recuperado = conflictoMongoService.recuperar(1)
        Assertions.assertEquals(conflicto1Recuperado, conflicto1)

        var conflictosRecuperados = conflictoMongoService.recuperarTodos()
        Assertions.assertEquals(1, conflictosRecuperados.size)
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto1))

        // Se crea y recupera el conflicto 2
        // el recuperarTodos() además contiene al conflicto1
        conflictoMongoService.crear(conflicto2)
        var conflicto2Recuperado = conflictoMongoService.recuperar(2)
        Assertions.assertEquals(conflicto2Recuperado, conflicto2)
        conflictosRecuperados = conflictoMongoService.recuperarTodos()
        Assertions.assertEquals(2, conflictosRecuperados.size)
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto1))
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto2))

        // Se crea y recupera el conflicto 2
        // el recuperarTodos() además contiene al conflicto1 y conflicto2
        conflictoMongoService.crear(conflicto3)
        var conflicto3Recuperado = conflictoMongoService.recuperar(3)
        Assertions.assertEquals(conflicto3Recuperado, conflicto3)
        conflictosRecuperados = conflictoMongoService.recuperarTodos()
        Assertions.assertEquals(3, conflictosRecuperados.size)
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto1))
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto2))
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto3))
    }

    @Test
    fun `se actualiza un conflicto siendo resuelto`() {
        // Se crea y recupera el conflicto 1
        conflictoMongoService.crear(conflicto1)
        var conflicto1Recuperado = conflictoMongoService.recuperar(1)
        Assertions.assertEquals(conflicto1Recuperado, conflicto1)
        var conflictosRecuperados = conflictoMongoService.recuperarTodos()
        Assertions.assertEquals(1, conflictosRecuperados.size)
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto1))

        // Se observa que el conflicto está sin resolver
        Assertions.assertTrue(!conflicto1Recuperado.resuelto)

        conflicto1.resuelto = true
        conflictoMongoService.actualizar(conflicto1)

        // Se observa que el conflicto está resuelto y persistido
        conflicto1Recuperado = conflictoMongoService.recuperar(1)
        Assertions.assertTrue(conflicto1Recuperado.resuelto)
    }

    @Test
    fun `se crea el conflicto1 y se elimina`() {
        // Se crea y recupera el conflicto 1
        conflictoMongoService.crear(conflicto1)
        var conflicto1Recuperado = conflictoMongoService.recuperar(1)
        Assertions.assertEquals(conflicto1Recuperado, conflicto1)

        var conflictosRecuperados = conflictoMongoService.recuperarTodos()

        Assertions.assertEquals(1, conflictosRecuperados.size)
        Assertions.assertTrue(conflictosRecuperados.contains(conflicto1))

        // Se elimina el conflicto1 y se observa que el recuperar todos es vacio
        conflictoMongoService.eliminar(1)
        conflictosRecuperados = conflictoMongoService.recuperarTodos()
        Assertions.assertEquals(0, conflictosRecuperados.size)
    }

    @Test
    fun `no se puede recuperar el conflicto con id 5 porque no existe`() {
        val id = 5
        try {
            conflictoMongoService.recuperar(id.toLong())
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `no se puede eliminar el conflicto con id 10 porque no existe`() {
        val id = 10
        try {
            conflictoMongoService.eliminar(id.toLong())
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `obtener un conflicto aleatorio entre conflicto1 y conflicto2 porque el conflicto3 supera 1km`() {
        // Se crean los conflictos 1, 2 y 3
        conflictoMongoService.crear(conflicto1)
        conflictoMongoService.crear(conflicto2)
        conflictoMongoService.crear(conflicto3)
        Assertions.assertEquals(3, conflictoMongoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto1))
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto2))
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto3))

        val conflictoAleatorio = conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        Assertions.assertTrue(conflictoAleatorio == conflicto1 || conflictoAleatorio == conflicto2)
    }

    @Test
    fun `se obtiene únicamente el conflicto2 como conflicto aleatorio ya que no supera 1km y está sin resolver`() {
        // Se crean los conflictos 1, 2 y 3
        // Conflicto1 : dentro del area, pero está resuelto
        conflicto1.resuelto = true
        conflictoMongoService.crear(conflicto1)
        // Conflicto2 : dentro del area, y sin resolver
        conflictoMongoService.crear(conflicto2)
        // Conflicto3 : fuera del area, y sin resolver
        conflictoMongoService.crear(conflicto3)
        Assertions.assertEquals(3, conflictoMongoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto1))
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto2))
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto3))

        val conflictoAleatorio = conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        Assertions.assertTrue(conflictoAleatorio == conflicto2)
    }

    @Test
    fun `no se puede obtener un conflicto aleatorio porque no está dentro de 1km`() {
        // Se crea el conflicto 3
        conflictoMongoService.crear(conflicto3)
        Assertions.assertEquals(1, conflictoMongoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto3))

        try {
            conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "No hay ningún conflicto dentro de 1km de radio sin resolver.")
        }
    }

    @Test
    fun `no se puede obtener un conflicto aleatorio aunque esté dentro de 1km porque está resuelto`() {
        // Se crea el conflicto 1
        conflicto1.resuelto = true
        conflictoMongoService.crear(conflicto1)
        Assertions.assertEquals(1, conflictoMongoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto1))

        try {
            conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "No hay ningún conflicto dentro de 1km de radio sin resolver.")
        }
    }

    @AfterEach
    fun clear() {
        conflictoMongoService.clear()
    }
}