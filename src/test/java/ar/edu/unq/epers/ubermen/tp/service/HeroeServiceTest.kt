package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.persistence.*
import ar.edu.unq.epers.ubermen.tp.service.impl.*
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.SinPoderException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.VidaErroneaException
import org.junit.jupiter.api.*
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
class HeroeServiceTest {
    @Autowired
    lateinit var heroeDAO : HeroeDAO
    @Autowired
    lateinit var heroeMutacionDAO: HeroeMutacionDAO
    @Autowired
    lateinit var conflictoDAO : ConflictoDAO
    @Autowired
    lateinit var poderDAO : PoderDAO
    @Autowired
    lateinit var poderMutacionDAO: PoderMutacionDAO
    @Autowired
    lateinit var villanoDAO : VillanoDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var heroeMongoDA0: HeroeCoordenadaDAO
    @Autowired
    lateinit var villanoMongoDA0: VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    @Autowired
    lateinit var distritoDAO: DistritoDAO

    lateinit var service: HeroeService
    lateinit var serviceConflicto: ConflictoService
    lateinit var servicePoder: PoderService
    lateinit var serviceVillano: VillanoService
    lateinit var servicePruebaDeHabilidad: PruebaDeHabilidadService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var heroeMongoService: HeroeMongoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var heroeNeoService: HeroeNeoService
    lateinit var distritoService: DistritoService
    lateinit var dataService: DataService

    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
    var conflictos: MutableSet<Conflicto> = mutableSetOf<Conflicto>()
    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA
    lateinit var spiderMan: Heroe
    lateinit var flash: Heroe
    lateinit var superMan: Heroe
    var superFuerza : Poder = Poder("Super Fuerza", fuerza, 50)
    var volar : Poder = Poder("Volar", destreza, 35)
    var rapidez : Poder = Poder("Rapidez", destreza, 30)
    var conflicto: Conflicto = Conflicto("conflicto1")

    var coordenada = GeoJsonPoint(-34.7183625463922, -58.35154565194343)

    @BeforeEach
    fun prepare() {
        this.heroeNeoService = HeroeNeoServiceImp(heroeMutacionDAO)
        this.villanoMongoService = VillanoMongoServiceImp(villanoMongoDA0)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeMongoDA0)
        this.serviceVillano = VillanoServiceImp(villanoDAO, villanoMongoService)
        this.servicePruebaDeHabilidad = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.serviceConflicto = ConflictoServiceImp(conflictoDAO, serviceVillano, servicePruebaDeHabilidad, conflictoMongoService, villanoMongoService, distritoService)
        this.servicePoder = PoderServiceImp(poderDAO, poderMutacionDAO)
        this.service = HeroeServiceImp(heroeDAO, serviceConflicto, heroeNeoService, heroeMongoService)
        this.dataService = DataServiceImp(servicePoder, service, serviceConflicto, servicePruebaDeHabilidad, serviceVillano)

        superFuerza = Poder("Super Fuerza", fuerza, 50)
        servicePoder.crear(superFuerza)

        volar = Poder("Volar", destreza, 35)
        servicePoder.crear(volar)

        rapidez = Poder("Rapidez", destreza, 30)
        servicePoder.crear(rapidez)

        serviceConflicto.crear(conflicto)

        poderes = mutableSetOf<Poder>()
        conflictos = mutableSetOf<Conflicto>()
    }

    @Test
    fun seCreaHeroeFlashCon100deVidaYPoderRapidezTest() {
        flash = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)
        val heroe = service.crear(flash)

        val heroeId = heroe.id!!.toInt()
        var flashRecuperado = service.recuperar(heroeId)
        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(flash, flashRecuperado)
        Assertions.assertEquals("Flash", flashRecuperado!!.nombre)
        Assertions.assertEquals(100, flashRecuperado.vida)
        Assertions.assertEquals("imagen2", flashRecuperado.imagenURL)
        Assertions.assertEquals(1, flashRecuperado.poderes.size)
        Assertions.assertEquals(0, flashRecuperado.conflictosResueltos.size)
        Assertions.assertEquals(1, flashRecuperado.getFuerza())
        Assertions.assertEquals(1, flashRecuperado.getConstitucion())
        Assertions.assertEquals(31, flashRecuperado.getDestreza())
        Assertions.assertEquals(1, flashRecuperado.getInteligencia())
    }

    @Test
    fun NoSePuedeCrearUnHeroeConVidaNegativaTest() {
        flash = Heroe("Flash", -10, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)
        try {
            service.crear(flash)
            Assertions.fail("Expected a VidaErroneaException to be thrown")
        } catch (e: VidaErroneaException) {
            Assertions.assertEquals(e.message, "Debe tener una vida correcta(de 0 a 100).")
        }
    }

    @Test
    fun NoSePuedeCrearUnHeroeConVidaMayorA100Test() {
        flash = Heroe("Flash", 110, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)
        try {
            service.crear(flash)
            Assertions.fail("Expected a VidaErroneaException to be thrown")
        } catch (e: VidaErroneaException) {
            Assertions.assertEquals(e.message, "Debe tener una vida correcta(de 0 a 100).")
        }
    }

    @Test
    fun NoSePuedeCrearUnHeroeSinUnPoderTest() {
        flash = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        try {
            service.crear(flash)
        } catch (e: SinPoderException) {
            Assertions.assertEquals(e.message, "Debe tener al menos un poder.")
        }
    }

    @Test
    fun noSePuedeCrearUnHeroeConNombreVacioTest() {
        flash = Heroe("", 100, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)
        try {
            service.crear(flash)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

   @Test
    fun seActualizaUnHeroeSumandoVidaDe10a50Test() {
        superMan = Heroe("Superman", 10, "imagen3", conflictos, poderes)
        superMan.poseerPoder(rapidez)
        var heroe = service.crear(superMan)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

       var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

       heroeMongo.cambiarCoordenada(coordenada, "1")
       heroeMongoService.actualizar(heroeMongo)

       Assertions.assertEquals(10, superManRecuperado!!.vida)

        superMan.sumarVida(40)
        heroe = service.actualizar(superMan)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

       Assertions.assertEquals(50, superManActualizado!!.vida)
    }

    @Test
    fun seActualizaUnHeroeSumandole20DeVidaTeniendo90Test() {
        superMan = Heroe("Superman", 90, "imagen3", conflictos, poderes)
        superMan.poseerPoder(rapidez)
        var heroe = service.crear(superMan)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(90, superManRecuperado!!.vida)

        superMan.sumarVida(20)
        heroe = service.actualizar(superMan)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

        Assertions.assertEquals(100, superManActualizado!!.vida)
    }

    @Test
    fun seActualizaUnHeroeRestandoVidaDe100a10Test() {
        var heroe = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        heroe.poseerPoder(rapidez)
        heroe = service.crear(heroe)
        var heroeId = heroe.id!!.toInt()
        var heroeRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(100, heroeRecuperado!!.vida)

        heroe.restarVida(90)
        heroe = service.actualizar(heroe)
        heroeId = heroe.id!!.toInt()

        Assertions.assertEquals(10, heroe.vida)

        var heroeActualizado = service.recuperar(heroeId)

        Assertions.assertEquals(10, heroeActualizado!!.vida)
    }

    @Test
    fun seActualizaHeroeRestandoVidaA0YHeroeMuereTest() {
        var heroe = Heroe("Superman", 90, "imagen3", conflictos, poderes)
        heroe.poseerPoder(rapidez)
        heroe = service.crear(heroe)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(90, superManRecuperado!!.vida)
        Assertions.assertFalse(superManRecuperado.estaMuerto())

        heroe.restarVida(90)
        heroe = service.actualizar(heroe)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

        Assertions.assertEquals(0, superManActualizado!!.vida)
        Assertions.assertTrue(superManActualizado.estaMuerto())
    }

    @Test
    fun actualizaHeroeModificandoSuNombreTest() {
        superMan = Heroe("SuperMan", 100, "imagen3", conflictos, poderes)
        superMan.poseerPoder(rapidez)
        var heroe = service.crear(superMan)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals("SuperMan", superManRecuperado!!.nombre)

        superMan.cambiarNombre("Super Man")
        heroe = service.actualizar(superMan)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

        Assertions.assertEquals("Super Man", superManActualizado!!.nombre)
    }

    @Test
    fun noSePuedeActualizarUnHeroeConNombreVacioTest() {
        superMan = Heroe("SuperMan", 100, "imagen1", conflictos, poderes)

        superMan.poseerPoder(rapidez)
        var heroe = service.crear(superMan)
        var heroeId = heroe.id!!.toInt()

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals("SuperMan", heroe.nombre)

        try {
            superMan.cambiarNombre("")
            service.actualizar(superMan)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

   @Test
    fun seActualizaHeroeAgregandoUnConflictoTest() {
        superMan = Heroe("SuperMan", 100, "imagen1", conflictos, poderes)

        superMan.poseerPoder(rapidez)
        var heroe = service.crear(superMan)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

       var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

       heroeMongo.cambiarCoordenada(coordenada, "1")
       heroeMongoService.actualizar(heroeMongo)

       Assertions.assertEquals(0, superManRecuperado!!.conflictosResueltos.size)

        var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
        var invisibilidad = Poder("Invisibilidad", destreza, 30, mutableListOf("invisibilidad"))
        poderes += invisibilidad
        servicePoder.crear(invisibilidad)
        var thanos = Villano("Thanos",100, "imagen1", mutableSetOf<Conflicto>(), poderes)
        serviceVillano.crear(thanos)
        var villanoMongo = villanoMongoService.recuperar(thanos.id!!)

       villanoMongo.cambiarCoordenada(coordenada, "1")
       villanoMongoService.actualizar(villanoMongo)

        var conflictoCreado = serviceConflicto.crearConflicto(thanos.id!!.toInt(),"conflictoCreado")
        service.resolverConflicto(heroeId, conflictoCreado.id!!.toInt())
        heroe = service.actualizar(superMan)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

       Assertions.assertEquals(1, superManActualizado!!.conflictosResueltos.size)
    }

    @Test
    fun seActualizaUnHeroeAgregandoleUnNuevoPoderTest() {
        var superHeroe = Heroe("SuperHeroe", 100, "imagen1", conflictos, poderes)
        superHeroe.poseerPoder(superFuerza)

        var heroe = service.crear(superHeroe)
        var heroeId = heroe.id!!.toInt()
        var heroeRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(1, heroeRecuperado!!.poderes.size)
        Assertions.assertEquals(51, heroeRecuperado.getFuerza())
        Assertions.assertEquals(1, heroeRecuperado.getDestreza())

        superHeroe.poseerPoder(rapidez)
        heroe = service.actualizar(superHeroe)
        heroeId = heroe.id!!.toInt()

        var heroeActualizado = service.recuperar(heroeId)

        Assertions.assertEquals(2, heroeActualizado!!.poderes.size)
        Assertions.assertEquals(51, heroeActualizado.getFuerza())
        Assertions.assertEquals(31, heroeActualizado.getDestreza())
    }

    @Test
    fun seActualizaUnHeroePorqueSeEliminaUnPoderTest() {
        var superHeroe = Heroe("Heroe", 100, "imagen1", conflictos, poderes)
        superHeroe.poseerPoder(superFuerza)
        superHeroe.poseerPoder(volar)
        var heroe = service.crear(superHeroe)
        var heroeId = heroe.id!!.toInt()
        var superManRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(2, superManRecuperado!!.poderes.size)
        Assertions.assertEquals(51, superManRecuperado.getFuerza())
        Assertions.assertEquals(36, superManRecuperado.getDestreza())

        superHeroe.olvidarPoder(superFuerza)
        heroe = service.actualizar(superHeroe)
        heroeId = heroe.id!!.toInt()

        var superManActualizado = service.recuperar(heroeId)

        Assertions.assertEquals(1, superManActualizado!!.poderes.size)
        Assertions.assertEquals(1, superManActualizado.getFuerza())
        Assertions.assertEquals(36, superManActualizado.getDestreza())
    }

    @Test
    fun seRecuperaHeroeFlashTest() {
        flash = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)

        var heroe = service.crear(flash)
        var heroeId = heroe.id!!.toInt()
        val flashRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(flash, flashRecuperado)
    }

    @Test
    fun noSePuedeRecuperarHeroeConIdInexistenteTest() {
        val heroeId = 50
        try {
            service.recuperar(heroeId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${heroeId}] no existe.")
        }
    }

    @Test
    fun recuperarTodosLosHeroesTest() {
        var poderes1 = poderes
        var conflictos1 = conflictos
        flash = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        spiderMan = Heroe("SpiderMan", 100, "imagen1", conflictos1, poderes1)
        flash.poseerPoder(rapidez)
        spiderMan.poseerPoder(rapidez)

        service.crear(flash)
        service.crear(spiderMan)

        var flashMongo = heroeMongoService.recuperar(flash.id!!)

        flashMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(flashMongo)

        var spiderManMongo = heroeMongoService.recuperar(spiderMan.id!!)

        spiderManMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(spiderManMongo)

        var heroesRecuperados = service.recuperarTodos()

        Assertions.assertEquals(2, heroesRecuperados.size)
        Assertions.assertTrue(heroesRecuperados.contains(flash))
        Assertions.assertTrue(heroesRecuperados.contains(spiderMan))
    }

    @Test
    fun recuperarTodosLosHeroesVacioTest() {
        Assertions.assertTrue(service.recuperarTodos().isEmpty())
    }

    @Test
    fun seEliminaHeroeFlashTest() {
        Assertions.assertEquals(0, service.recuperarTodos().size)

        flash = Heroe("Flash", 100, "imagen2", conflictos, poderes)
        flash.poseerPoder(rapidez)
        var heroe = service.crear(flash)
        var heroeId = heroe.id!!.toInt()
        var flashRecuperado = service.recuperar(heroeId)

        var heroeMongo = heroeMongoService.recuperar(heroeId.toLong())

        heroeMongo.cambiarCoordenada(coordenada, "1")
        heroeMongoService.actualizar(heroeMongo)

        Assertions.assertEquals(flash, flashRecuperado)
        Assertions.assertEquals(1, service.recuperarTodos().size)
        Assertions.assertTrue(service.recuperarTodos().contains(flash))

        service.eliminar(heroeId)

        Assertions.assertEquals(0, service.recuperarTodos().size)
        Assertions.assertFalse(service.recuperarTodos().contains(flash))
    }

    @Test
    fun noSePuedeEliminarHeroeQueNoExisteTest() {
        val heroeId = 70
        try {
            service.eliminar(heroeId)
            Assertions.fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${heroeId}] no existe.")
        }
    }

    @AfterEach
    fun cleanup() {
        serviceConflicto.clear()
        service.clear()
        serviceVillano.clear()
        servicePoder.clear()
    }
}