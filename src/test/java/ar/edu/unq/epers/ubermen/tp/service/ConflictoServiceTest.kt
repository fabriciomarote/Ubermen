package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.ValorMayorException
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
class ConflictoServiceTest {

    @Autowired
    lateinit var conflictoDAO: ConflictoDAO
    @Autowired
    lateinit var villanoDAO: VillanoDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var poderDAO: PoderDAO
    @Autowired
    lateinit var poderMutacionDAO : PoderMutacionDAO
    @Autowired
    lateinit var villanoCoordenadaDAO : VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    @Autowired
    lateinit var heroeCoordenadaDAO : HeroeCoordenadaDAO
    @Autowired
    lateinit var distritoDAO : DistritoDAO

    lateinit var conflictoService : ConflictoService
    lateinit var villanoService: VillanoService
    lateinit var pruebaDeHabilidadService: PruebaDeHabilidadService
    lateinit var poderService: PoderService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var heroeMongoService: HeroeMongoService
    lateinit var distritoService: DistritoService

    var constitucion : Atributo = Atributo.CONSTITUCION
    var fuerza : Atributo = Atributo.FUERZA
    var inteligencia : Atributo = Atributo.INTELIGENCIA
    var destreza : Atributo = Atributo.DESTREZA
    lateinit var conflicto1 : Conflicto
    lateinit var conflicto2 : Conflicto
    lateinit var conflicto3 : Conflicto
    lateinit var villano1 : Villano
    lateinit var villanoMongo1: VillanoMongo
    lateinit var poder1 : Poder
    lateinit var poder2 : Poder
    lateinit var poder3 : Poder
    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()
    var conflictosComenzados: MutableSet<Conflicto> = mutableSetOf<Conflicto>()
    var nombresPosiblesPoder1: MutableList<String> = mutableListOf<String>()
    var nombresPosiblesPoder2: MutableList<String> = mutableListOf<String>()
    var nombresPosiblesPoder3: MutableList<String> = mutableListOf<String>()
    lateinit var conflictoCreadoPorVillano: Conflicto

    @BeforeEach
    fun prepare() {
        this.poderService = PoderServiceImp(poderDAO, poderMutacionDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.villanoMongoService = VillanoMongoServiceImp(villanoCoordenadaDAO)
        this.villanoService = VillanoServiceImp(villanoDAO, villanoMongoService)
        this.pruebaDeHabilidadService = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.conflictoService = ConflictoServiceImp(conflictoDAO, villanoService, pruebaDeHabilidadService, conflictoMongoService, villanoMongoService, distritoService)

        conflicto1 = Conflicto("conflicto1")
        conflicto2 = Conflicto("conflicto2")
        conflicto3 =  Conflicto("conflicto3")

        nombresPosiblesPoder1.add("nombrePosible1P1")
        nombresPosiblesPoder1.add("nombrePosible2P1")
        nombresPosiblesPoder1.add("nombrePosible3P1")

        nombresPosiblesPoder2.add("nombrePosible1P2")
        nombresPosiblesPoder2.add("nombrePosible2P2")
        nombresPosiblesPoder2.add("nombrePosible3P2")

        nombresPosiblesPoder3.add("nombrePosible1P3")
        nombresPosiblesPoder3.add("nombrePosible2P3")
        nombresPosiblesPoder3.add("nombrePosible3P3")

        poderes = mutableSetOf<Poder>()
        conflictosComenzados = mutableSetOf<Conflicto>()

        poder1 = Poder("poder1", fuerza, 10, nombresPosiblesPoder1)
        poderService.crear(poder1)
        poder2 = Poder("poder2", destreza, 5, nombresPosiblesPoder2)
        poderService.crear(poder2)
        poder3 = Poder("poder3", inteligencia, 3, nombresPosiblesPoder3)
        poderService.crear(poder3)

        villano1 = Villano("villano1", 100, "img1", conflictosComenzados, poderes)

        villano1.poseerPoder(poder1)
        villano1.poseerPoder(poder2)
        villano1.poseerPoder(poder3)
        villanoService.crear(villano1)

        var coordenada = GeoJsonPoint(-34.7183625463922, -58.35154565194343)
        villanoMongo1 = villanoMongoService.recuperar(villano1.id!!)
        villanoMongo1.cambiarCoordenada(coordenada, "1")
        villanoMongoService.actualizar(villanoMongo1)
    }

    @Test
    fun nombreDeConflictoCreadoPorVillanoTest() {
        conflictoCreadoPorVillano = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflictoNuevo")
        Assertions.assertEquals("conflictoNuevo", conflictoCreadoPorVillano.nombre)
    }

    @Test
    fun creadorDeConflictoCreadoPorVillanoTest() {
        conflictoCreadoPorVillano = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflictoNuevo")
        Assertions.assertEquals(villano1, conflictoCreadoPorVillano.creadoPor)
    }

    @Test
    fun poderesFavorablesDeConflictoCreadoPorVillanoTest() {
        conflictoCreadoPorVillano = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflictoNuevo")
        Assertions.assertEquals(villano1.poderes.size, conflictoCreadoPorVillano.poderesFavorables.size)
        //print(conflictoNuevo.poderesFavorables.first())
        //Assert.assertTrue(conflictoNuevo.poderesFavorables.contains(poder1))
        /*Assert.assertTrue(conflictoNuevo.poderesFavorables.contains(poder2))
        Assert.assertTrue(conflictoNuevo.poderesFavorables.contains(poder3))*/
    }

    @Test
    fun atributoDePruebaHabilidadDeConflictoCreadoPorVillanoTest() {

    }

    @Test
    fun nombrePosibleDePruebaHabilidadDeConflictoCreadoPorVillanoTest() {
        GeneradorRandom.cambiarStrategy(FixedStrategy("nombrePosible3P1"))
        conflictoCreadoPorVillano = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflictoNuevo")
        Assertions.assertEquals(conflictoCreadoPorVillano.pruebas.first().nombre, "nombrePosible3P1")

        GeneradorRandom.cambiarStrategy(RandomStrategy)
    }

    @Test
    fun dificultadPosibleDePruebaHabilidadDeConflictoCreadoPorVillanoTest() {
        GeneradorRandom.cambiarStrategy(FixedStrategy(1))
        GeneradorRandom.cambiarStrategy(FixedStrategy("nombrePosible3P1"))
        conflictoCreadoPorVillano = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflictoNuevo")
        Assertions.assertEquals(conflictoCreadoPorVillano.pruebas.first().dificultad, 0)

        GeneradorRandom.cambiarStrategy(RandomStrategy)
    }

    @Test
    fun seCreaConflicto1Test() {
        conflicto1 = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflicto1")
        val conflictoId = conflicto1.id!!.toInt()

        var conflictoRecuperado = conflictoService.recuperar(conflictoId)

        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertEquals("conflicto1", conflictoRecuperado!!.nombre)
        Assertions.assertEquals(0, conflictoRecuperado.progresoHaciaSuResolucion)
        Assertions.assertEquals(null, conflictoRecuperado.resueltoPor)
        Assertions.assertEquals(villano1, conflictoRecuperado.creadoPor)
    }

    @Test
    fun noSePuedeCrearUnConflictoConNombreVacioTest() {
        conflicto3 = Conflicto("")
        try {
            conflictoService.crear(conflicto3)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seActualizaNombreDeConflicto1Test() {
        conflicto1 = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflicto1")
        val conflictoId = conflicto1.id!!.toInt()

        var conflictoRecuperado = this.conflictoService.recuperar(conflictoId)

        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertEquals("conflicto1", conflictoRecuperado!!.nombre)

        conflicto1.cambiarNombre("nuevo conflicto")
        this.conflictoService.actualizar(conflicto1)

        conflictoRecuperado = this.conflictoService.recuperar(conflictoId)

        // Se verifica que se recupera el conflicto y el progresoHaciaSuResolucion es 1
        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertEquals("nuevo conflicto", conflictoRecuperado!!.nombre)
    }

    @Test
    fun noSePuedeActualizarUnConflictoConNombreVacioTest() {
        var conflicto = conflictoService.crear(conflicto3)
        var conflictoId = conflicto.id!!.toInt()

        var conflictoRecuperado = conflictoService.recuperar(conflictoId)

        Assertions.assertEquals("conflicto3", conflictoRecuperado!!.nombre)

        try {
            conflicto3.cambiarNombre("")
            conflictoService.actualizar(conflicto3)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun seCambiaProgresoDe0a5DeConflicto1Test() {
        conflicto1 = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflicto1")
        val conflictoId = conflicto1.id!!.toInt()

        var conflictoRecuperado = this.conflictoService.recuperar(conflictoId)

        // Se verifica que se recupera el conflicto y el progresoHaciaSuResolucion se inicializa en 0
        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertEquals(0, conflictoRecuperado!!.progresoHaciaSuResolucion)

        conflicto1.cambiarProgreso(5)
        this.conflictoService.actualizar(conflicto1)

        conflictoRecuperado = this.conflictoService.recuperar(conflictoId)

        // Se verifica que se recupera el conflicto y el progresoHaciaSuResolucion es 1
        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertEquals(5, conflictoRecuperado!!.progresoHaciaSuResolucion)
    }

    @Test
    fun noSePuedeSetearProgresoDeConflictoConNumeroNegativoTest() {
        var conflicto = conflictoService.crear(conflicto1)
        var conflictoId = conflicto.id!!.toInt()

        var conflictoRecuperado = conflictoService.recuperar(conflictoId)

        Assertions.assertEquals(0, conflictoRecuperado!!.progresoHaciaSuResolucion)

        try {
            conflicto1.cambiarProgreso(-1)
            conflictoService.actualizar(conflicto1)
        } catch (e: CantidadNegativaException) {
            Assertions.assertEquals(e.message, "La cantidad no puede ser negativa.")
        }
    }

    @Test
    fun noSePuedeSetearProgresoDeConflictoConMayorA10Test() {
        val conflicto = conflicto1
        conflictoService.crear(conflicto)

        try {
            conflicto.cambiarProgreso(11)
            conflictoService.actualizar(conflicto)
        } catch (e: ValorMayorException) {
            Assertions.assertEquals(e.message, "El valor no puede ser mayor a 10.")
        }
    }

    @Test
    fun seRecuperaConflicto2Test() {
        var conflicto = conflictoService.crear(conflicto2)
        val conflictoId = conflicto.id!!.toInt()
        val conflictoRecuperado = this.conflictoService.recuperar(conflictoId)

        Assertions.assertEquals(conflicto2, conflictoRecuperado)
        Assertions.assertEquals("conflicto2", conflictoRecuperado!!.nombre)
        Assertions.assertEquals(0, conflictoRecuperado.progresoHaciaSuResolucion)
        Assertions.assertEquals(0, conflictoRecuperado.pruebas.size)
        Assertions.assertEquals(null, conflictoRecuperado.resueltoPor)
        Assertions.assertEquals(null, conflictoRecuperado.creadoPor)
    }

    @Test
    fun noSePuederecuperarUnConflictoConIdInexistenteTest() {
        // Se supone que el conflictoId no está en la base de datos
        val conflictoId = 25
        try {
            conflictoService.recuperar(conflictoId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${conflictoId}] no existe.")
        }
    }

    @Test
    fun seRecuperanTodosLosConflictosTest() {
        conflicto1 = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflicto1")
        conflictoService.crear(conflicto2)

        // Se comprueba que la lista contenga conflicto1, conflicto2 y que no contenga a conflicto3
        Assertions.assertEquals(2, conflictoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto1))
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto2))
        Assertions.assertFalse(conflictoService.recuperarTodos().contains(conflicto3))

        conflictoService.crear(conflicto3)

        // Se comprueba que la lista contenga conflicto1, conflicto2 y conflicto3
        Assertions.assertEquals(3, conflictoService.recuperarTodos().size)
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto1))
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto2))
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto3))

        // Se elimina el conflicto 1 y se comprueba que la lista ya no lo contiene
        conflictoService.eliminar(conflicto1.id!!.toInt())
        Assertions.assertEquals(2, conflictoService.recuperarTodos().size)
        Assertions.assertFalse(conflictoService.recuperarTodos().contains(conflicto1))
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto2))
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto3))
    }

    @Test
    fun seRecuperanTodosVacioTest() {
        // Se usa la función recuperarTodos sin crear conflictos con el servicio
        // La función devolverá una lista vacía, ya que no hay conflictos
        Assertions.assertTrue(conflictoService.recuperarTodos().isEmpty())
    }

    @Test
    fun seEliminaConflicto1Test() {
        conflicto1 = conflictoService.crearConflicto(villano1.id!!.toInt(), "conflicto1")
        val conflictoId = conflicto1.id!!.toInt()
        val conflictoRecuperado = conflictoService.recuperar(conflictoId)

        // El conflicto existe en la base de datos
        Assertions.assertEquals(conflicto1, conflictoRecuperado)
        Assertions.assertTrue(conflictoService.recuperarTodos().contains(conflicto1))

        conflictoService.eliminar(conflictoId)

        // Verifica que el conflicto no existe más en la base de datos
        Assertions.assertFalse(conflictoService.recuperarTodos().contains(conflicto1))
        try {
            conflictoService.recuperar(conflictoId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${conflictoId}] no existe.")
        }
    }

    @Test
    fun noSePuedeEliminarConflictoQueNoExisteTest() {
        // Se supone que el conflictoId no está en la base de datos
        val conflictoId = 25
        try {
            conflictoService.eliminar(conflictoId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id [${conflictoId}] no existe.")
        }
    }

    @AfterEach
    fun cleanup() {
        conflictoService.clear()
        villanoService.clear()
        poderService.clear()
        pruebaDeHabilidadService.clear()
    }
}