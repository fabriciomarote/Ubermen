package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.persistence.*
import ar.edu.unq.epers.ubermen.tp.service.impl.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RankingServiceTest {

    @Autowired
    lateinit var heroeDAO : HeroeDAO
    @Autowired
    lateinit var heroeMutacionDAO: HeroeMutacionDAO
    @Autowired
    lateinit var poderDAO : PoderDAO
    @Autowired
    lateinit var poderMutacionDAO: PoderMutacionDAO
    @Autowired
    lateinit var villanoDAO : VillanoDAO
    @Autowired
    lateinit var conflictoDAO : ConflictoDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    @Autowired
    lateinit var villanoCoordenadaDAO: VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    @Autowired
    lateinit var distritoDAO: DistritoDAO

    lateinit var service: RankingService
    lateinit var heroeService: HeroeService
    lateinit var poderService: PoderService
    lateinit var villanoService: VillanoService
    lateinit var conflictoService: ConflictoService
    lateinit var pruebaDeHabilidadService: PruebaDeHabilidadService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var heroeMongoService: HeroeMongoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var heroeNeoService: HeroeNeoService
    lateinit var distritoService: DistritoService
    lateinit var dataService: DataService

    var ascendente : Direccion = Direccion.ASCENDENTE
    var descendente : Direccion = Direccion.DESCENDENTE

    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var destreza : Atributo = Atributo.DESTREZA

    lateinit var poderes1: MutableSet<Poder>
    lateinit var poderes2: MutableSet<Poder>
    lateinit var poderes3: MutableSet<Poder>
    lateinit var poderes4: MutableSet<Poder>
    lateinit var poderes5: MutableSet<Poder>
    lateinit var poderes6: MutableSet<Poder>
    lateinit var poderes7: MutableSet<Poder>
    lateinit var poderes8: MutableSet<Poder>
    lateinit var poderes9: MutableSet<Poder>
    lateinit var poderes10: MutableSet<Poder>
    lateinit var poderes11: MutableSet<Poder>
    lateinit var poderes12: MutableSet<Poder>
    lateinit var poderes13: MutableSet<Poder>
    lateinit var poderes14: MutableSet<Poder>
    lateinit var poderes15: MutableSet<Poder>
    lateinit var poderes16: MutableSet<Poder>
    lateinit var poderes17: MutableSet<Poder>
    lateinit var poderes18: MutableSet<Poder>
    lateinit var poderes19: MutableSet<Poder>
    lateinit var poderes20: MutableSet<Poder>
    lateinit var poderes21: MutableSet<Poder>
    lateinit var poderes22: MutableSet<Poder>
    lateinit var poderes23: MutableSet<Poder>
    lateinit var poderes24: MutableSet<Poder>
    lateinit var poderes25: MutableSet<Poder>
    lateinit var poderes26: MutableSet<Poder>
    lateinit var poderes27: MutableSet<Poder>
    lateinit var poderes28: MutableSet<Poder>
    lateinit var poderes29: MutableSet<Poder>
    lateinit var poderes30: MutableSet<Poder>

    lateinit var conflictos: MutableSet<Conflicto>

    lateinit var spiderman: Heroe
    lateinit var flash: Heroe
    lateinit var superman: Heroe
    lateinit var capitanAmerica: Heroe
    lateinit var blackPanther: Heroe
    lateinit var aquaman: Heroe
    lateinit var antMan: Heroe
    lateinit var thor: Heroe
    lateinit var hulk: Heroe
    lateinit var ironMan: Heroe
    lateinit var batman: Heroe
    lateinit var wonderWoman: Heroe
    lateinit var cyborg: Heroe
    lateinit var hawkeye: Heroe
    lateinit var shazam: Heroe

    lateinit var thanos: Villano
    lateinit var blackAdam: Villano
    lateinit var venom: Villano
    lateinit var duendeVerde: Villano
    lateinit var joker: Villano
    lateinit var ultron: Villano
    lateinit var kang: Villano
    lateinit var drOctopus: Villano
    lateinit var electro: Villano
    lateinit var morbius: Villano
    lateinit var harleyQuinn: Villano
    lateinit var bane: Villano
    lateinit var acertijo: Villano
    lateinit var pinguino: Villano
    lateinit var gatubela: Villano

    lateinit var superFuerza : Poder
    lateinit var volar : Poder
    lateinit var regeneramiento : Poder
    lateinit var rapidez : Poder

    lateinit var conflicto1: Conflicto
    lateinit var conflicto2: Conflicto
    lateinit var conflicto3: Conflicto
    lateinit var conflicto4: Conflicto
    lateinit var conflicto5: Conflicto
    lateinit var conflicto6: Conflicto
    lateinit var conflicto7: Conflicto
    lateinit var conflicto8: Conflicto
    lateinit var conflicto9: Conflicto
    lateinit var conflicto10: Conflicto
    lateinit var conflicto11: Conflicto
    lateinit var conflicto12: Conflicto
    lateinit var conflicto13: Conflicto
    lateinit var conflicto14: Conflicto
    lateinit var conflicto15: Conflicto
    lateinit var conflicto16: Conflicto
    lateinit var conflicto17: Conflicto
    lateinit var conflicto18: Conflicto
    lateinit var conflicto19: Conflicto
    lateinit var conflicto20: Conflicto

    @BeforeEach
    fun prepare() {
        this.heroeNeoService = HeroeNeoServiceImp(heroeMutacionDAO)
        this.villanoMongoService = VillanoMongoServiceImp(villanoCoordenadaDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.pruebaDeHabilidadService = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.villanoService = VillanoServiceImp(villanoDAO, villanoMongoService)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.conflictoService = ConflictoServiceImp(conflictoDAO, villanoService, pruebaDeHabilidadService, conflictoMongoService, villanoMongoService, distritoService)
        this.poderService = PoderServiceImp(poderDAO, poderMutacionDAO)
        this.heroeService = HeroeServiceImp(heroeDAO, conflictoService, heroeNeoService, heroeMongoService)
        this.service = RankingServiceImp(heroeService, villanoService)
        this.dataService = DataServiceImp(poderService, heroeService, conflictoService, pruebaDeHabilidadService, villanoService)

        poderes1 = mutableSetOf<Poder>()
        poderes2 = mutableSetOf<Poder>()
        poderes3 = mutableSetOf<Poder>()
        poderes4 = mutableSetOf<Poder>()
        poderes5 = mutableSetOf<Poder>()
        poderes6 = mutableSetOf<Poder>()
        poderes7 = mutableSetOf<Poder>()
        poderes8 = mutableSetOf<Poder>()
        poderes9 = mutableSetOf<Poder>()
        poderes10 = mutableSetOf<Poder>()
        poderes11 = mutableSetOf<Poder>()
        poderes12 = mutableSetOf<Poder>()
        poderes13 = mutableSetOf<Poder>()
        poderes14 = mutableSetOf<Poder>()
        poderes15 = mutableSetOf<Poder>()
        poderes16 = mutableSetOf<Poder>()
        poderes17 = mutableSetOf<Poder>()
        poderes18 = mutableSetOf<Poder>()
        poderes19 = mutableSetOf<Poder>()
        poderes20 = mutableSetOf<Poder>()
        poderes21 = mutableSetOf<Poder>()
        poderes22 = mutableSetOf<Poder>()
        poderes23 = mutableSetOf<Poder>()
        poderes24 = mutableSetOf<Poder>()
        poderes25 = mutableSetOf<Poder>()
        poderes26 = mutableSetOf<Poder>()
        poderes27 = mutableSetOf<Poder>()
        poderes28 = mutableSetOf<Poder>()
        poderes29 = mutableSetOf<Poder>()
        poderes30 = mutableSetOf<Poder>()

        conflictos = mutableSetOf<Conflicto>()

        spiderman = Heroe("Spiderman", 100, "http://spiderman.jpg", conflictos, poderes1)
        flash = Heroe("Flash", 100, "http://flash.jpg", conflictos, poderes2)
        superman = Heroe("Superman", 100, "http://superman.jpg", conflictos, poderes3)
        capitanAmerica = Heroe("Capitan America", 100, "http://capitanAmerica.jpg", conflictos, poderes4)
        blackPanther = Heroe("Black Panther", 100, "http://blackPanther.jpg", conflictos, poderes5)
        aquaman = Heroe("Aquaman", 100, "http://aquaman.jpg", conflictos, poderes6)
        antMan = Heroe("Antman", 100, "http://antman.jpg", conflictos, poderes7)
        thor = Heroe("Thor", 100, "http://thor.jpg", conflictos, poderes8)
        hulk = Heroe("Hulk", 100, "http://hulk.jpg", conflictos, poderes9)
        ironMan = Heroe("Iron Man", 100, "http://ironMan.jpg", conflictos, poderes10)
        batman = Heroe("Batman", 100, "http://batman.jpg", conflictos, poderes11)
        wonderWoman = Heroe("Wonder Woman", 100, "http://wonderWoman.jpg", conflictos, poderes12)
        cyborg = Heroe("Cyborg", 100, "http://cyborg.jpg", conflictos, poderes13)
        hawkeye = Heroe("Hawkeye", 100, "http://hawkeye.jpg", conflictos, poderes14)
        shazam = Heroe("Shazam", 100, "http://shazam.jpg", conflictos, poderes15)

        thanos = Villano("Thanos", 100, "http://thanos.jpg", conflictos, poderes16)
        blackAdam = Villano("Black Adam", 100, "http://blackAdam.jpg", conflictos, poderes17)
        venom = Villano("Venom", 100, "http://venom.jpg", conflictos, poderes18)
        duendeVerde = Villano("Duende Verde", 100, "http://duendeVerde.jpg", conflictos, poderes19)
        joker = Villano("Joker", 100, "http://joker.jpg", conflictos, poderes20)
        ultron = Villano("Ultron", 100, "http://ultron.jpg", conflictos, poderes21)
        kang = Villano("Kang", 100, "http://kang.jpg", conflictos, poderes22)
        drOctopus = Villano("Dr.Octopus", 100, "http://drOctopus.jpg", conflictos, poderes23)
        electro = Villano("Electro", 100, "http://electro.jpg", conflictos, poderes24)
        morbius = Villano("Morbius", 100, "http://morbius.jpg", conflictos, poderes25)
        harleyQuinn = Villano("Hayley Quinn", 100, "http://harleyQuinn.jpg", conflictos, poderes26)
        bane = Villano("Bane", 100, "http://bane.jpg", conflictos, poderes27)
        acertijo = Villano("Acertijo", 100, "http://acertijo.jpg", conflictos, poderes28)
        pinguino = Villano("Pinguino", 100, "http://pinguino.jpg", conflictos, poderes29)
        gatubela = Villano("Gatubela", 100, "http://gatubela.jpg", conflictos, poderes30)

        superFuerza = Poder("Super Fuerza", fuerza, 10, mutableListOf("nombre1"))
        volar = Poder("Volar", destreza, 5, mutableListOf("nombre1"))
        regeneramiento = Poder("Regeneramiento", constitucion, 4, mutableListOf("nombre1"))
        rapidez = Poder("Rapidez", destreza, 3, mutableListOf("nombre1"))

        // Se persisten los poderes
        poderService.crear(superFuerza)
        poderService.crear(volar)
        poderService.crear(rapidez)
        poderService.crear(regeneramiento)

        // Se agregan poderes a heroes
        spiderman.poseerPoder(superFuerza)
        spiderman.poseerPoder(rapidez)
        spiderman.poseerPoder(regeneramiento)
        superman.poseerPoder(superFuerza)
        superman.poseerPoder(regeneramiento)
        superman.poseerPoder(volar)
        superman.poseerPoder(rapidez)
        flash.poseerPoder(rapidez)
        flash.poseerPoder(regeneramiento)
        capitanAmerica.poseerPoder(superFuerza)
        blackPanther.poseerPoder(superFuerza)
        blackPanther.poseerPoder(rapidez)
        aquaman.poseerPoder(superFuerza)
        antMan.poseerPoder(superFuerza)
        thor.poseerPoder(superFuerza)
        thor.poseerPoder(rapidez)
        hulk.poseerPoder(superFuerza)
        hulk.poseerPoder(regeneramiento)
        ironMan.poseerPoder(superFuerza)
        batman.poseerPoder(superFuerza)
        wonderWoman.poseerPoder(superFuerza)
        wonderWoman.poseerPoder(rapidez)
        wonderWoman.poseerPoder(volar)
        cyborg.poseerPoder(volar)
        cyborg.poseerPoder(superFuerza)
        hawkeye.poseerPoder(rapidez)
        shazam.poseerPoder(volar)
        shazam.poseerPoder(regeneramiento)
        shazam.poseerPoder(superFuerza)

        // Se agregan poderes a villanos
        thanos.poseerPoder(superFuerza)
        thanos.poseerPoder(rapidez)
        blackAdam.poseerPoder(superFuerza)
        blackAdam.poseerPoder(regeneramiento)
        blackAdam.poseerPoder(rapidez)
        blackAdam.poseerPoder(volar)
        venom.poseerPoder(superFuerza)
        venom.poseerPoder(regeneramiento)
        duendeVerde.poseerPoder(volar)
        duendeVerde.poseerPoder(superFuerza)
        joker.poseerPoder(rapidez)
        ultron.poseerPoder(superFuerza)
        ultron.poseerPoder(volar)
        ultron.poseerPoder(rapidez)
        kang.poseerPoder(superFuerza)
        drOctopus.poseerPoder(superFuerza)
        electro.poseerPoder(regeneramiento)
        electro.poseerPoder(rapidez)
        morbius.poseerPoder(regeneramiento)
        morbius.poseerPoder(volar)
        morbius.poseerPoder(superFuerza)
        morbius.poseerPoder(rapidez)
        harleyQuinn.poseerPoder(superFuerza)
        bane.poseerPoder(superFuerza)
        bane.poseerPoder(rapidez)
        acertijo.poseerPoder(superFuerza)
        pinguino.poseerPoder(regeneramiento)
        gatubela.poseerPoder(rapidez)

        // Se persisten los heroes

        heroeService.crear(spiderman)
        heroeService.crear(flash)
        heroeService.crear(superman)
        heroeService.crear(capitanAmerica)
        heroeService.crear(blackPanther)
        heroeService.crear(aquaman)
        heroeService.crear(antMan)
        heroeService.crear(thor)
        heroeService.crear(hulk)
        heroeService.crear(ironMan)
        heroeService.crear(batman)
        heroeService.crear(wonderWoman)
        heroeService.crear(cyborg)
        heroeService.crear(hawkeye)
        heroeService.crear(shazam)

        var coordenada = GeoJsonPoint(-34.7183625463922, -58.35154565194343)

        // Se persisten los villanos y se actualizan agregando una coordenada a VillanoMongo
        thanos = villanoService.crear(thanos)
        var villanoMongo = villanoMongoService.recuperar(thanos.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        blackAdam = villanoService.crear(blackAdam)
        villanoMongo = villanoMongoService.recuperar(blackAdam.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        venom = villanoService.crear(venom)
        villanoMongo = villanoMongoService.recuperar(venom.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        duendeVerde = villanoService.crear(duendeVerde)
        villanoMongo = villanoMongoService.recuperar(duendeVerde.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        joker = villanoService.crear(joker)
        villanoMongo = villanoMongoService.recuperar(joker.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        ultron = villanoService.crear(ultron)
        villanoMongo = villanoMongoService.recuperar(ultron.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        kang = villanoService.crear(kang)
        villanoMongo = villanoMongoService.recuperar(kang.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        drOctopus = villanoService.crear(drOctopus)
        villanoMongo = villanoMongoService.recuperar(drOctopus.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        electro = villanoService.crear(electro)
        villanoMongo = villanoMongoService.recuperar(electro.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        morbius = villanoService.crear(morbius)
        villanoMongo = villanoMongoService.recuperar(morbius.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        harleyQuinn = villanoService.crear(harleyQuinn)
        villanoMongo = villanoMongoService.recuperar(harleyQuinn.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        bane = villanoService.crear(bane)
        villanoMongo = villanoMongoService.recuperar(bane.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        acertijo = villanoService.crear(acertijo)
        villanoMongo = villanoMongoService.recuperar(acertijo.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        pinguino = villanoService.crear(pinguino)
        villanoMongo = villanoMongoService.recuperar(pinguino.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        gatubela = villanoService.crear(gatubela)
        villanoMongo = villanoMongoService.recuperar(gatubela.id!!.toLong())
        villanoMongo.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo)

        //Se persisten los conflictos con sus respectivos villanos que los crean
        conflicto1 = conflictoService.crearConflicto(thanos.id!!.toInt(), "conflicto1")
        conflicto2 = conflictoService.crearConflicto(thanos.id!!.toInt(), "conflicto2")
        conflicto3 = conflictoService.crearConflicto(thanos.id!!.toInt(), "conflicto3")

        conflicto4 = conflictoService.crearConflicto(blackAdam.id!!.toInt(), "conflicto4")
        conflicto5 = conflictoService.crearConflicto(blackAdam.id!!.toInt(), "conflicto5")

        conflicto6 = conflictoService.crearConflicto(venom.id!!.toInt(), "conflicto6")
        conflicto7 = conflictoService.crearConflicto(venom.id!!.toInt(), "conflicto7")

        conflicto8 = conflictoService.crearConflicto(ultron.id!!.toInt(), "conflicto8")
        conflicto9 = conflictoService.crearConflicto(ultron.id!!.toInt(), "conflicto9")
        conflicto10 = conflictoService.crearConflicto(ultron.id!!.toInt(), "conflicto10")

        conflicto11 = conflictoService.crearConflicto(electro.id!!.toInt(), "conflicto11")

        conflicto12 = conflictoService.crearConflicto(duendeVerde.id!!.toInt(), "conflicto12")
        conflicto13 = conflictoService.crearConflicto(duendeVerde.id!!.toInt(), "conflicto13")

        conflicto14 = conflictoService.crearConflicto(bane.id!!.toInt(), "conflicto14")
        conflicto15 = conflictoService.crearConflicto(bane.id!!.toInt(), "conflicto15")

        conflicto16 = conflictoService.crearConflicto(kang.id!!.toInt(), "conflicto16")

        conflicto17 = conflictoService.crearConflicto(acertijo.id!!.toInt(), "conflicto17")

        conflicto18 = conflictoService.crearConflicto(morbius.id!!.toInt(), "conflicto18")
        conflicto19 = conflictoService.crearConflicto(morbius.id!!.toInt(), "conflicto19")

        conflicto20 = conflictoService.crearConflicto(drOctopus.id!!.toInt(), "conflicto20")

        // Se agregan conflictos a los heroes

        heroeService.resolverConflicto(spiderman.id!!.toInt(), conflicto1.id!!.toInt())
        heroeService.resolverConflicto(spiderman.id!!.toInt(), conflicto2.id!!.toInt())
        heroeService.resolverConflicto(superman.id!!.toInt(), conflicto4.id!!.toInt())
        heroeService.resolverConflicto(superman.id!!.toInt(), conflicto5.id!!.toInt())
        heroeService.resolverConflicto(superman.id!!.toInt(), conflicto6.id!!.toInt())
        heroeService.resolverConflicto(flash.id!!.toInt(), conflicto8.id!!.toInt())
        heroeService.resolverConflicto(capitanAmerica.id!!.toInt(), conflicto9.id!!.toInt())
        heroeService.resolverConflicto(capitanAmerica.id!!.toInt(), conflicto10.id!!.toInt())
        heroeService.resolverConflicto(blackPanther.id!!.toInt(), conflicto11.id!!.toInt())
        heroeService.resolverConflicto(aquaman.id!!.toInt(), conflicto12.id!!.toInt())
        heroeService.resolverConflicto(antMan.id!!.toInt(), conflicto13.id!!.toInt())
        heroeService.resolverConflicto(thor.id!!.toInt(), conflicto14.id!!.toInt())
        heroeService.resolverConflicto(wonderWoman.id!!.toInt(), conflicto15.id!!.toInt())
        heroeService.resolverConflicto(wonderWoman.id!!.toInt(), conflicto16.id!!.toInt())
        heroeService.resolverConflicto(cyborg.id!!.toInt(), conflicto18.id!!.toInt())
        heroeService.resolverConflicto(shazam.id!!.toInt(), conflicto19.id!!.toInt())
        heroeService.resolverConflicto(shazam.id!!.toInt(), conflicto20.id!!.toInt())

    }

    @Test
    fun losMasPoderososDeManeraAscendentePagina0DevuelvePrimeroAAntmanTest() {
        val losMasPoderosos = service.losMasPoderosos(ascendente, 0)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Antman", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraAscendentePagina1DevuelvePrimeroAIronManTest() {
        val losMasPoderosos = service.losMasPoderosos(ascendente, 1)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Iron Man", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraAscendentePagina2DevuelvePrimeroAThorTest() {
        val losMasPoderosos = service.losMasPoderosos(ascendente, 2)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Thor", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraAscendentePagina3DevuelveVacioTest() {
        val losMasPoderosos = service.losMasPoderosos(ascendente, 3)
        Assertions.assertEquals(0, losMasPoderosos.size)
        Assertions.assertTrue(losMasPoderosos.isEmpty())
    }

    @Test
    fun losMasPoderososDeManeraDescendentePagina0DevuelvePrimeroASupermanTest() {
        val losMasPoderosos = service.losMasPoderosos(descendente, 0)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Superman", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraDescendentePagina1DevuelvePrimeroACyborgTest() {
        val losMasPoderosos = service.losMasPoderosos(descendente, 1)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Cyborg", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraDescendentePagina2DevuelvePrimeroATest() {
        val losMasPoderosos = service.losMasPoderosos(descendente, 2)
        Assertions.assertEquals(5, losMasPoderosos.size)
        Assertions.assertEquals("Aquaman", losMasPoderosos.first().nombre)
    }

    @Test
    fun losMasPoderososDeManeraDescendentePagina3DevuelveVacioTest() {
        val losMasPoderosos = service.losMasPoderosos(descendente, 3)
        Assertions.assertEquals(0, losMasPoderosos.size)
        Assertions.assertTrue(losMasPoderosos.isEmpty())
    }

    @Test
    fun guardianesDeManeraAscendentePagina0DevuelvePrimeroABatmanTest() {
        val losGuardianes = service.guardianes(ascendente, 0)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Batman", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraAscendentePagina1DevuelvePrimeroAAquamanTest() {
        val losGuardianes = service.guardianes(ascendente, 1)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Aquaman", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraAscendentePagina2DevuelvePrimeroACapitanAmericaTest() {
        val losGuardianes = service.guardianes(ascendente, 2)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Capitan America", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraAscendentePagina3DevuelveVacioTest() {
        val losGuardianes = service.guardianes(ascendente, 3)
        Assertions.assertEquals(0, losGuardianes.size)
        Assertions.assertTrue(losGuardianes.isEmpty())
    }

    @Test
    fun guardianesDeManeraDescendentePagina0DevuelvePrimeroASupermanTest() {
        val losGuardianes = service.guardianes(descendente, 0)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Superman", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraDescendentePagina1DevuelvePrimeroAThorTest() {
        val losGuardianes = service.guardianes(descendente, 1)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Antman", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraDescendentePagina2DevuelvePrimeroAAntmanTest() {
        val losGuardianes = service.guardianes(descendente, 2)
        Assertions.assertEquals(5, losGuardianes.size)
        Assertions.assertEquals("Thor", losGuardianes.first().nombre)
    }

    @Test
    fun guardianesDeManeraDescendentePagina3DevuelveVacioTest() {
        val losGuardianes = service.guardianes(descendente, 3)
        Assertions.assertEquals(0, losGuardianes.size)
        Assertions.assertTrue(losGuardianes.isEmpty())
    }

    @Test
    fun hyperVillanosDeManeraAscendenteDesdePagina0Test() {
        val hyperVillanos = service.hyperVillanos(ascendente, 0)
        Assertions.assertEquals(5, hyperVillanos.size)
        Assertions.assertEquals("Acertijo", hyperVillanos.get(0).nombre)
        Assertions.assertEquals("Gatubela", hyperVillanos.get(1).nombre)
        Assertions.assertEquals("Hayley Quinn", hyperVillanos.get(2).nombre)
        Assertions.assertEquals("Joker", hyperVillanos.get(3).nombre)
        Assertions.assertEquals("Pinguino", hyperVillanos.get(4).nombre)
    }


    @Test
    fun hyperVillanosDeManeraDescendenteDesdePagina0Test() {
        val hyperVillanos = service.hyperVillanos(descendente, 0)
        Assertions.assertEquals(5, hyperVillanos.size)
        Assertions.assertEquals("Bane", hyperVillanos.get(0).nombre)
        Assertions.assertEquals("Black Adam", hyperVillanos.get(1).nombre)
        Assertions.assertEquals("Duende Verde", hyperVillanos.get(2).nombre)
        Assertions.assertEquals("Morbius", hyperVillanos.get(3).nombre)
        Assertions.assertEquals("Thanos", hyperVillanos.get(4).nombre)
    }

    @AfterEach
    fun cleanup() {
        conflictoService.clear()
        heroeService.clear()
        villanoService.clear()
        poderService.clear()
        pruebaDeHabilidadService.clear()
    }
}