package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.Villano
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.persistence.*
import ar.edu.unq.epers.ubermen.tp.service.impl.*
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.SinPoderException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.VidaErroneaException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class VillanoServiceTest {

    @Autowired
    lateinit var villanoDAO : VillanoDAO
    @Autowired
    lateinit var conflictoDAO : ConflictoDAO
    @Autowired
    lateinit var poderDAO : PoderDAO
    @Autowired
    lateinit var poderMutacionDAO: PoderMutacionDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var villanoCoordenadaDAO: VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    @Autowired
    lateinit var distritoDAO: DistritoDAO

    lateinit var service: VillanoService
    lateinit var serviceConflicto: ConflictoService
    lateinit var servicePoder: PoderService
    lateinit var servicePruebaDeHabilidad: PruebaDeHabilidadService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var heroeMongoService : HeroeMongoService
    lateinit var distritoService: DistritoService

    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
    var conflictosComenzados: MutableSet<Conflicto> = mutableSetOf<Conflicto>()
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA
    var thanos: Villano = Villano("Thanos",100, "imagen1", conflictosComenzados, poderes)
    var duendeVerde: Villano = Villano("Duende Verde", 100, "imagen2", conflictosComenzados, poderes)
    var venom : Villano = Villano("Venom", 100, "imagen3", conflictosComenzados, poderes)
    var blackAdam: Villano = Villano("Black Adam",100, "imagen4", conflictosComenzados, poderes)
    var superFuerza : Poder = Poder("Super Fuerza", fuerza, 50)
    var volar : Poder = Poder("Volar", destreza, 35)
    var regeneramiento : Poder = Poder("Regeneramiento", constitucion, 40)
    var rapidez : Poder = Poder("Rapidez", destreza, 30)

    @BeforeEach
    fun prepare() {
        this.villanoMongoService = VillanoMongoServiceImp(villanoCoordenadaDAO)
        this.service = VillanoServiceImp(villanoDAO, villanoMongoService)
        this.servicePruebaDeHabilidad = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.serviceConflicto = ConflictoServiceImp(conflictoDAO, service, servicePruebaDeHabilidad, conflictoMongoService, villanoMongoService, distritoService)
        this.servicePoder = PoderServiceImp(poderDAO,poderMutacionDAO)

        poderes = mutableSetOf<Poder>()
        conflictosComenzados = mutableSetOf<Conflicto>()

        superFuerza = Poder("Super Fuerza", fuerza, 50)
        servicePoder.crear(superFuerza)
        volar = Poder("Volar", destreza, 35)
        servicePoder.crear(volar)
        regeneramiento = Poder("Regeneramiento", constitucion, 40)
        servicePoder.crear(regeneramiento)
        rapidez = Poder("Rapidez", destreza, 30, mutableListOf("rapidez"))
        servicePoder.crear(rapidez)

        thanos = Villano("Thanos",100, "imagen1", conflictosComenzados, poderes)
        duendeVerde = Villano("Duende Verde", 100, "imagen2", conflictosComenzados, poderes)
        venom = Villano("Venom", 100, "imagen3", conflictosComenzados, poderes)
        blackAdam = Villano("Black Adam",100, "imagen4", conflictosComenzados, poderes)
    }

    @Test
    fun seCreaVillanoThanosCon100deVidaYPoderSuperFuerzaTest() {
        thanos = Villano("Thanos",100, "imagen1", conflictosComenzados, poderes)
        thanos.poseerPoder(superFuerza)
        val villano = service.crear(thanos)

        val villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(thanos, villanoRecuperado)
        Assertions.assertEquals("Thanos", villanoRecuperado.nombre)
        Assertions.assertEquals(100, villanoRecuperado.vida)
        Assertions.assertEquals("imagen1", villanoRecuperado.imagenURL)
        Assertions.assertEquals(1, villanoRecuperado.poderes.size)
        Assertions.assertEquals(0, villanoRecuperado.conflictosComenzados.size)
        Assertions.assertEquals(51, villanoRecuperado.getFuerza())
        Assertions.assertEquals(1, villanoRecuperado.getConstitucion())
        Assertions.assertEquals(1, villanoRecuperado.getDestreza())
        Assertions.assertEquals(1, villanoRecuperado.getInteligencia())
    }

    @Test
    fun NoSePuedeCrearUnVillanoConVidaNegativaTest(){
        thanos = Villano("Thanos", -10, "imagen2", conflictosComenzados, poderes)
        thanos.poseerPoder(superFuerza)

        try {
            service.crear(thanos)
            Assertions.fail("Expected a VidaErroneaException to be thrown")
        } catch (e: VidaErroneaException) {
            Assertions.assertEquals(e.message, "Debe tener una vida correcta(de 0 a 100).")
        }
    }

    @Test
    fun NoSePuedeCrearUnVillanoConVidaMayorA100Test(){
        thanos = Villano("Thanos", 110, "imagen2", conflictosComenzados, poderes)
        thanos.poseerPoder(superFuerza)

        try {
            service.crear(thanos)
            Assertions.fail("Expected a VidaErroneaException to be thrown")
        } catch (e: VidaErroneaException) {
            Assertions.assertEquals(e.message, "Debe tener una vida correcta(de 0 a 100).")
        }
    }


    @Test
    fun NoSePuedeCrearUnVillanoSinUnPoderTest(){
        thanos = Villano("Thanos", 100, "imagen2", conflictosComenzados, poderes)

        try {
            service.crear(thanos)
            Assertions.fail("Expected a SinPoderException to be thrown")
        } catch (e: SinPoderException) {
            Assertions.assertEquals(e.message, "Debe tener al menos un poder.")
        }
    }

    @Test
    fun noSePuedeCrearUnVillanoConNombreVacioTest() {
        thanos = Villano("", 100, "imagen1", conflictosComenzados, poderes)
        thanos.poseerPoder(superFuerza)

        try {
            service.crear(thanos)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seActualizaUnVillanoSumandoVidaDe10a50Test() {
        duendeVerde = Villano("Duende Verde", 10, "imagen3", conflictosComenzados, poderes)
        duendeVerde.poseerPoder(superFuerza)
        var villano = service.crear(duendeVerde)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(10, villanoRecuperado.vida)

        duendeVerde.sumarVida(40)
        villano = service.actualizar(duendeVerde)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(50, villanoActualizado.vida)
    }

    @Test
    fun seActualizaUnVillanoSumandole20DeVidaTeniendo90Test() {
        duendeVerde = Villano("Duende Verde", 90, "imagen3", conflictosComenzados, poderes)
        duendeVerde.poseerPoder(superFuerza)
        var villano = service.crear(duendeVerde)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(90, villanoRecuperado.vida)

        duendeVerde.sumarVida(20)
        villano = service.actualizar(duendeVerde)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(100, villanoActualizado.vida)
    }

    @Test
    fun seActualizaUnVillanoRestandoVidaDe100a10Test() {
        duendeVerde.poseerPoder(superFuerza)
        var villano = service.crear(duendeVerde)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(100, villanoRecuperado.vida)

        duendeVerde.restarVida(90)
        villano = service.actualizar(duendeVerde)
        villanoId = villano.id!!.toInt()

        Assertions.assertEquals(10, duendeVerde.vida)

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(10, villanoActualizado.vida)
    }

    @Test
    fun seActualizaVillanoRestandoVidaA0YMuereTest() {
        duendeVerde.poseerPoder(superFuerza)
        var villano = service.crear(duendeVerde)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(100, villanoRecuperado.vida)
        Assertions.assertFalse(villanoRecuperado.estaMuerto())

        duendeVerde.restarVida(100)
        villano = service.actualizar(duendeVerde)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(0, villanoActualizado.vida)
        Assertions.assertTrue(villanoActualizado.estaMuerto())
    }

    @Test
    fun seActualizaVillanoModificandoSuNombreTest() {
        venom = Villano("Venom", 100, "imagen3", conflictosComenzados, poderes)
        venom.poseerPoder(rapidez)
        var villano = service.crear(venom)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals("Venom", villanoRecuperado.nombre)

        venom.cambiarNombre("Simbionte")
        villano = service.actualizar(venom)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals("Simbionte", villanoActualizado.nombre)
    }

    @Test
    fun noSePuedeActualizarUnVillanoConNombreVacioTest() {
        var villano = Villano("Black Adam", 100, "imagen1", conflictosComenzados, poderes)
        villano.poseerPoder(superFuerza)
        var villanoCreado = service.crear(villano)
        var villanoId = villanoCreado.id!!.toInt()

        var villanoRecuperado = service.recuperar(villanoId)
        Assertions.assertEquals("Black Adam", villanoRecuperado.nombre)


        try {
            villano.cambiarNombre("")
            service.actualizar(villano)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seActualizaVillanoAgregandoUnConflictoTest() {
        blackAdam.poseerPoder(rapidez)
        var villano = service.crear(blackAdam)
        var villanoId = villano.id!!.toInt()
        var villanoMongo = villanoMongoService.recuperar(villanoId.toLong())
        var villanoRecuperado = service.recuperar(villanoId)
        Assertions.assertEquals(0, villanoRecuperado.conflictosComenzados.size)

        var coordenada = GeoJsonPoint(-34.7183625463922, -58.35154565194343)
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        var conflicto = serviceConflicto.crearConflicto(villanoId, "conflicto1")

        conflicto.cambiarCreadoPor(blackAdam)
        serviceConflicto.actualizar(conflicto)
        blackAdam.agregarConflicto(conflicto)
        villano = service.actualizar(blackAdam)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(1, villanoActualizado.conflictosComenzados.size)
    }

    @Test
    fun seActualizaUnVillanoAgregandoleUnNuevoPoderTest() {
        blackAdam.poseerPoder(superFuerza)
        var villano = service.crear(blackAdam)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(1, villanoRecuperado.poderes.size)
        Assertions.assertEquals(51, villanoRecuperado.getFuerza())
        Assertions.assertEquals(1, villanoRecuperado.getDestreza())

        blackAdam.poseerPoder(volar)
        villano = service.actualizar(blackAdam)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(2, villanoActualizado.poderes.size)
        Assertions.assertEquals(51, villanoActualizado.getFuerza())
        Assertions.assertEquals(36, villanoActualizado.getDestreza())
    }

    @Test
    fun seActualizaUnVillanoPorqueSeEliminaUnPoderTest() {
        blackAdam.poseerPoder(superFuerza)
        blackAdam.poseerPoder(volar)
        var villano = service.crear(blackAdam)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(2, villanoRecuperado.poderes.size)
        Assertions.assertEquals(51, villanoRecuperado.getFuerza())
        Assertions.assertEquals(36, villanoRecuperado.getDestreza())

        blackAdam.olvidarPoder(superFuerza)
        villano = service.actualizar(blackAdam)
        villanoId = villano.id!!.toInt()

        var villanoActualizado = service.recuperar(villanoId)

        Assertions.assertEquals(1, villanoActualizado.poderes.size)
        Assertions.assertEquals(1, villanoActualizado.getFuerza())
        Assertions.assertEquals(36, villanoActualizado.getDestreza())
    }

    @Test
    fun seRecuperaVillanoThanosTest() {
        thanos.poseerPoder(rapidez)
        var villano = service.crear(thanos)
        var villanoId = villano.id!!.toInt()
        val villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(villano, villanoRecuperado)
    }

    @Test
    fun noSePuedeRecuperarVillanoConIdInexistenteTest() {
        val villanoId = 50

        try {
            service.recuperar(villanoId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${villanoId}] no existe.")
        }
    }

    @Test
    fun recuperarTodosLosVillanosTest() {
        thanos.poseerPoder(rapidez)
        blackAdam.poseerPoder(superFuerza)
        duendeVerde.poseerPoder(superFuerza)
        venom.poseerPoder(regeneramiento)

        service.crear(thanos)
        service.crear(blackAdam)
        service.crear(duendeVerde)
        service.crear(venom)

        var villanosRecuperados = service.recuperarTodos()

        Assertions.assertTrue(villanosRecuperados.size == 4)
        Assertions.assertTrue(villanosRecuperados.contains(thanos))
        Assertions.assertTrue(villanosRecuperados.contains(blackAdam))
        Assertions.assertTrue(villanosRecuperados.contains(duendeVerde))
        Assertions.assertTrue(villanosRecuperados.contains(venom))
    }

    @Test
    fun recuperarTodosLosVillanosVacioTest() {
        Assertions.assertTrue(service.recuperarTodos().isEmpty())
    }

    @Test
    fun seEliminaVillanoThanosTest() {
        thanos.poseerPoder(rapidez)
        var villano = service.crear(thanos)
        var villanoId = villano.id!!.toInt()
        var villanoRecuperado = service.recuperar(villanoId)

        Assertions.assertEquals(villano, villanoRecuperado)
        Assertions.assertTrue(service.recuperarTodos().contains(thanos))

        service.eliminar(villanoId)

        // Se prueba que el villano fue eliminado
        Assertions.assertFalse(service.recuperarTodos().contains(thanos))

        try {
            service.recuperar(villanoId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${villanoId}] no existe.")
        }
    }

    @Test
    fun noSePuedeEliminarVillanoQueNoExisteTest() {
        val villanoId = 70

        try {
            service.recuperar(villanoId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${villanoId}] no existe.")
        }
    }

    @AfterEach
    fun cleanup() {
        serviceConflicto.clear()
        service.clear()
        servicePruebaDeHabilidad.clear()
        servicePoder.clear()
    }
}