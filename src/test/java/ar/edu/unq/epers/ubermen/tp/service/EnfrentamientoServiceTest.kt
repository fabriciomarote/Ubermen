package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.persistence.*
import ar.edu.unq.epers.ubermen.tp.service.impl.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class EnfrentamientoServiceTest {
    @Autowired
    lateinit var heroeDAO: HeroeDAO
    @Autowired
    lateinit var heroeMutacionDao : HeroeMutacionDAO
    @Autowired
    lateinit var conflictoDAO : ConflictoDAO
    @Autowired
    lateinit var villanoDAO: VillanoDAO
    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    @Autowired
    lateinit var villanoCoordenadaDAO: VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO: ConflictoCoordenadaDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var poderDAO: PoderDAO
    @Autowired
    lateinit var poderMutacionDAO: PoderMutacionDAO
    @Autowired
    lateinit var distritoDAO : DistritoDAO

    lateinit var service : EnfrentamientoService
    lateinit var serviceHeroe : HeroeService
    lateinit var heroeMongoService : HeroeMongoService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var serviceConflicto : ConflictoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var serviceVillano : VillanoService
    lateinit var servicePruebaDeHabilidad: PruebaDeHabilidadService
    lateinit var poderService: PoderService
    lateinit var heroeNeoService: HeroeNeoService
    lateinit var distritoService: DistritoService

    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA

    lateinit var poderes: MutableSet<Poder>

    lateinit var poder : Poder

    lateinit var heroe: Heroe

    lateinit var villano1: Villano
    lateinit var villano2: Villano
    lateinit var villano3: Villano

    lateinit var villanoRecuperado1: Villano
    lateinit var villanoRecuperado2: Villano
    lateinit var villanoRecuperado3: Villano

    lateinit var conflicto1 : Conflicto
    lateinit var conflicto2 : Conflicto
    lateinit var conflicto3 : Conflicto

    lateinit var coordenadaVillano1 : GeoJsonPoint
    lateinit var coordenadaVillano2 : GeoJsonPoint
    lateinit var coordenadaVillano3 : GeoJsonPoint

    lateinit var heroeMongo : HeroeMongo
    lateinit var coordenadaHeroe : GeoJsonPoint

    @BeforeEach
    fun prepare() {
        this.heroeNeoService = HeroeNeoServiceImp(heroeMutacionDao)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.villanoMongoService = VillanoMongoServiceImp(villanoCoordenadaDAO)
        this.servicePruebaDeHabilidad = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.serviceVillano = VillanoServiceImp(villanoDAO, villanoMongoService)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.serviceConflicto = ConflictoServiceImp(conflictoDAO, serviceVillano, servicePruebaDeHabilidad, conflictoMongoService, villanoMongoService, distritoService)
        this.serviceHeroe = HeroeServiceImp(heroeDAO, serviceConflicto, heroeNeoService, heroeMongoService)
        this.poderService = PoderServiceImp(poderDAO, poderMutacionDAO)
        this.service = EnfrentamientoServiceImp(serviceHeroe, heroeMongoService, serviceConflicto, conflictoMongoService)

        poderes = mutableSetOf<Poder>()

        poder = Poder("Super Fuerza", fuerza, 10)

        heroe = Heroe("Spiderman", 100, "http://spiderman.jpg", mutableSetOf<Conflicto>(), poderes)
        villano1 = Villano("Thanos", 100, "http://thanos.jpg", mutableSetOf<Conflicto>(), poderes)
        villano2 = Villano("Black Adam", 100, "http://blackAdam.jpg", mutableSetOf<Conflicto>(), poderes)
        villano3 = Villano("Venom", 100, "http://venom.jpg", mutableSetOf<Conflicto>(), poderes)
        heroe.poseerPoder(poder)
        villano1.poseerPoder(poder)
        villano2.poseerPoder(poder)
        villano3.poseerPoder(poder)

        coordenadaVillano1 = GeoJsonPoint(-34.760951800003326, -58.20423290186426)
        coordenadaVillano2 = GeoJsonPoint(-34.76069377068813, -58.20723584123123)
        coordenadaVillano3 = GeoJsonPoint(-34.74236447331491, -58.22010277745575)

        coordenadaHeroe = GeoJsonPoint(-34.75948993378682, -58.20520374176037)

        poderService.crear(poder)

        poder.agregarNombrePosible("Pelea contra villano")

        poderService.actualizar(poder)

        serviceHeroe.crear(heroe)
        serviceVillano.crear(villano1)
        serviceVillano.crear(villano2)
        serviceVillano.crear(villano3)

        var heroeRecuperado = serviceHeroe.recuperar(heroe.id!!.toInt())
        villanoRecuperado1 = serviceVillano.recuperar(villano1.id!!.toInt())
        villanoRecuperado2 = serviceVillano.recuperar(villano2.id!!.toInt())
        villanoRecuperado3 = serviceVillano.recuperar(villano3.id!!.toInt())

        heroeMongo = heroeMongoService.recuperar(heroeRecuperado!!.id!!)
        var villanoMongo1 = villanoMongoService.recuperar(villanoRecuperado1!!.id!!)
        var villanoMongo2 = villanoMongoService.recuperar(villanoRecuperado2!!.id!!)
        var villanoMongo3 = villanoMongoService.recuperar(villanoRecuperado3!!.id!!)

        heroeMongo.cambiarCoordenada(coordenadaHeroe, "1")
        heroeMongoService.actualizar(heroeMongo)

        villanoMongo1.cambiarCoordenada(coordenadaVillano1, "2")
        villanoMongoService.actualizar(villanoMongo1)
        villanoMongo2.cambiarCoordenada(coordenadaVillano2, "3")
        villanoMongoService.actualizar(villanoMongo2)
        villanoMongo3.cambiarCoordenada(coordenadaVillano3, "4")
        villanoMongoService.actualizar(villanoMongo3)
    }

    @Test
    fun `obtener un conflicto aleatorio entre conflicto1 y conflicto2 porque el conflicto3 supera 1km`() {
        conflicto1 = serviceConflicto.crearConflicto(villanoRecuperado1.id!!.toInt(), "conflicto1")
        conflicto2 = serviceConflicto.crearConflicto(villanoRecuperado2.id!!.toInt(), "conflicto2")
        conflicto3 = serviceConflicto.crearConflicto(villanoRecuperado3.id!!.toInt(), "conflicto3")

        Assertions.assertEquals(3, conflictoMongoService.recuperarTodos().size)

        var conflictoMongoRecuperado1 = conflictoMongoService.recuperar(conflicto1.id!!)
        var conflictoMongoRecuperado2 = conflictoMongoService.recuperar(conflicto2.id!!)
        var conflictoMongoRecuperado3 = conflictoMongoService.recuperar(conflicto3.id!!)

        Assertions.assertEquals(conflicto1.id!!, conflictoMongoRecuperado1.idConflicto)
        Assertions.assertEquals(conflicto2.id!!, conflictoMongoRecuperado2.idConflicto)
        Assertions.assertEquals(conflicto3.id!!, conflictoMongoRecuperado3.idConflicto)

        var conflictoAleatorio = service.obtenerConflictoAleatorio(heroe.id!!)

        Assertions.assertTrue(conflictoAleatorio.id == conflicto1.id || conflictoAleatorio.id == conflicto2.id)
    }


    @Test
    fun `no se puede obtener un conflicto aleatorio porque no está dentro de 1km`() {
        // Se crea el conflicto 3
        conflicto3 = serviceConflicto.crearConflicto(villanoRecuperado3.id!!.toInt(), "conflicto3")
        Assertions.assertEquals(1, conflictoMongoService.recuperarTodos().size)

        var conflictoMongoRecuperado3 = conflictoMongoService.recuperar(conflicto3.id!!)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflictoMongoRecuperado3))

        try {
            conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "No hay ningún conflicto dentro de 1km de radio sin resolver.")
        }
    }

    @Test
    fun `no se puede obtener un conflicto aleatorio aunque esté dentro de 1km porque está resuelto`() {
        // Se crea el conflicto 1
        conflicto1 = serviceConflicto.crearConflicto(villanoRecuperado1.id!!.toInt(), "conflicto1")
        var conflicto1Mongo = conflictoMongoService.recuperar(conflicto1.id!!)
        conflicto1Mongo.resuelto = true
        conflictoMongoService.actualizar(conflicto1Mongo)
        Assertions.assertEquals(1, conflictoMongoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoMongoService.recuperarTodos().contains(conflicto1Mongo))

        try {
            conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "No hay ningún conflicto dentro de 1km de radio sin resolver.")
        }
    }

    @AfterEach
    fun cleanup() {
        serviceHeroe.clear()
        serviceConflicto.clear()
        serviceVillano.clear()
        //service.clear()
    }
}