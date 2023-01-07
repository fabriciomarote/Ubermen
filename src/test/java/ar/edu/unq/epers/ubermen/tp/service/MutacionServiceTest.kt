package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.persistence.*
import ar.edu.unq.epers.ubermen.tp.service.impl.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MutacionServiceTest {

    @Autowired
    lateinit var heroeDao: HeroeDAO
    @Autowired
    lateinit var villanoDao: VillanoDAO
    @Autowired
    lateinit var heroeMutacionDao : HeroeMutacionDAO
    @Autowired
    lateinit var poderDao: PoderDAO
    @Autowired
    lateinit var pruebaDeHabilidadDAO: PruebaDeHabilidadDAO
    @Autowired
    lateinit var poderMutacionDao : PoderMutacionDAO
    @Autowired
    lateinit var conflictoDAO: ConflictoDAO
    @Autowired
    lateinit var heroeCoordenadaDAO: HeroeCoordenadaDAO
    @Autowired
    lateinit var villanoCoordenadaDAO: VillanoCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO
    @Autowired
    lateinit var distritoDAO: DistritoDAO

    lateinit var mutacionService: MutacionService
    lateinit var poderService: PoderService
    lateinit var heroeService: HeroeService
    lateinit var poderNeoService: PoderNeoService
    lateinit var heroeNeoService: HeroeNeoService
    lateinit var conflictoService : ConflictoService
    lateinit var villanoService: VillanoService
    lateinit var pruebaDeHabilidadService: PruebaDeHabilidadService
    lateinit var villanoMongoService: VillanoMongoService
    lateinit var heroeMongoService: HeroeMongoService
    lateinit var conflictoMongoService: ConflictoMongoService
    lateinit var distritoService: DistritoService

    var poderes: MutableSet<Poder> = mutableSetOf<Poder>()

    lateinit var condicion1: Condicion
    lateinit var condicion2: Condicion
    lateinit var condicion3: Condicion
    lateinit var condicion4: Condicion
    lateinit var condicion5: Condicion
    lateinit var condicion6: Condicion
    lateinit var condicion7: Condicion
    lateinit var condicion8: Condicion
    lateinit var condicion9: Condicion
    lateinit var condicion10: Condicion
    lateinit var condicion11: Condicion
    lateinit var condicion12: Condicion
    lateinit var condicion13: Condicion

    lateinit var heroe: Heroe
    lateinit var poder1: Poder
    lateinit var poder2: Poder
    lateinit var poder3: Poder
    lateinit var poder4: Poder
    lateinit var poder5: Poder
    lateinit var poder6: Poder
    lateinit var poder7: Poder
    lateinit var poder8: Poder
    lateinit var poder9: Poder
    lateinit var poder10: Poder

    lateinit var poderNeo: PoderNeo

    var pruebasDeHabilidad = Evaluacion.PRUEBASDEHABILIDAD
    var exitosDeTiradas = Evaluacion.EXITOSDETIRADAS
    var fuerza = Atributo.FUERZA
    var destreza = Atributo.DESTREZA
    var constitucion = Atributo.CONSTITUCION
    var superadas = Resultado.SUPERADAS
    var falladas = Resultado.FALLADAS
    var acumulados = Resultado.ACUMULADOS

    @BeforeEach
    fun prepare() {
        this.heroeNeoService = HeroeNeoServiceImp(heroeMutacionDao)
        this.villanoMongoService = VillanoMongoServiceImp(villanoCoordenadaDAO)
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.villanoService = VillanoServiceImp(villanoDao, villanoMongoService)
        this.pruebaDeHabilidadService = PruebaDeHabilidadServiceImp(pruebaDeHabilidadDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)
        this.conflictoService = ConflictoServiceImp(conflictoDAO, villanoService, pruebaDeHabilidadService, conflictoMongoService, villanoMongoService, distritoService)
        this.heroeService = HeroeServiceImp(heroeDao, conflictoService, heroeNeoService, heroeMongoService)
        this.poderService = PoderServiceImp(poderDao, poderMutacionDao)
        this.poderNeoService = PoderNeoServiceImp(poderMutacionDao)
        this.mutacionService = MutacionServiceImp(poderService, heroeService, poderNeoService, heroeNeoService)

        heroe = Heroe("Superman", poderes)

        condicion1 = Condicion(2, pruebasDeHabilidad, fuerza, superadas)
        condicion2 = Condicion(2, pruebasDeHabilidad, destreza, falladas)
        condicion3 = Condicion(3, exitosDeTiradas, fuerza, acumulados)
        condicion4 = Condicion(1, pruebasDeHabilidad, constitucion, falladas)
        condicion5 = Condicion(4, pruebasDeHabilidad, fuerza, superadas)
        condicion6 = Condicion(2, pruebasDeHabilidad, destreza, falladas)
        condicion7 = Condicion(1, exitosDeTiradas, constitucion, acumulados)
        condicion8 = Condicion(1, exitosDeTiradas, destreza, superadas)
        condicion9 = Condicion(5, pruebasDeHabilidad, fuerza, superadas)
        condicion10 = Condicion(2, pruebasDeHabilidad, destreza, falladas)
        condicion11 = Condicion(2,exitosDeTiradas, fuerza, superadas)
        condicion12 = Condicion(6,pruebasDeHabilidad, fuerza, falladas)
        condicion13 = Condicion(6,pruebasDeHabilidad, destreza, superadas)

        poder1 = Poder("SuperFuerza")
        poder2 = Poder("Volar")
        poder3 = Poder("Rapidez")
        poder4 = Poder("RayosLaser")
        poder5 = Poder("SuperVelocidad")
        poder6 = Poder("Leer Mentes")
        poder7 = Poder("Elasticidad")
        poder8 = Poder("Control Sismico")
        poder9 = Poder("Sexto Sentido")
        poder10 = Poder("Energia Cosmica")

        poderNeo = PoderNeo()

        poderService.crear(poder1)
        poderService.crear(poder2)
        poderService.crear(poder3)
        poderService.crear(poder4)
        poderService.crear(poder5)
        poderService.crear(poder6)
        poderService.crear(poder7)
        poderService.crear(poder8)
        poderService.crear(poder9)
        poderService.crear(poder10)

        heroe.poseerPoder(poder1)
        heroeService.crear(heroe)

        mutacionService.crearMutacion(poder1.id!!, poder2.id!!, condicion1)
        mutacionService.crearMutacion(poder2.id!!, poder3.id!!, condicion2)
        mutacionService.crearMutacion(poder2.id!!, poder4.id!!, condicion3)
        mutacionService.crearMutacion(poder4.id!!, poder5.id!!, condicion4)
    }

    @Test
    fun noSePuedeCrearMutacionPorQueNoExisteElPoder3Test() {
        var idPoder3: Long = 33
        try {
            mutacionService.crearMutacion(poder1.id!!, idPoder3, condicion1)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }
    @Test
    fun noSePuedeCrearMutacionPorQueLaCantidadDeLaCondicionEsNegativaTest() {
        try {
            var condicion2 = Condicion(-1,pruebasDeHabilidad, fuerza, superadas)
            mutacionService.crearMutacion(poder1.id!!, poder2.id!!, condicion2)
        } catch (e: RuntimeException) {poder2.id!!
            Assertions.assertEquals(e.message, "La cantidad debe ser mayor a 0.")
        }
    }

    @Test
    fun noSePuedeCrearMutacionPorLaEvaluacionPasadaDeLaCondicionTest() {
        try {
            var condicion2 = Condicion(3,pruebasDeHabilidad, destreza, superadas)
            mutacionService.crearMutacion(poder1.id!!, poder2.id!!, condicion2)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "La evaluacion debe ser pruebas de habilidad o exitos de tiradas.")
        }
    }
    @Test
    fun noSePuedeCrearMutacionPorElAtributoPasadoDeLaCondicionTest() {
        try {
            var condicion2 = Condicion(2,pruebasDeHabilidad, destreza, superadas)
            mutacionService.crearMutacion(poder1.id!!, poder2.id!!, condicion2)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El atributo debe ser Inteligencia, Destreza, Fuerza o Constitucion.")
        }
    }
    @Test
    fun noSePuedeCrearMutacionPorElResultadoPasadoDeLaCondicionTest() {
        try {
            var condicion2 = Condicion(4,pruebasDeHabilidad, fuerza, falladas)
            mutacionService.crearMutacion(poder1.id!!, poder2.id!!, condicion2)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El resultado debe ser superadas, falladas o acumulados.")
        }
    }

    //Se crean y chequean dos mutaciones con condiciones distintas para un poder
    @Test
    fun seCreanDosMutacionesEnUnPoderTest() {
        mutacionService.crearMutacion(poder5.id!!, poder1.id!!, condicion3)
        mutacionService.crearMutacion(poder5.id!!, poder2.id!!, condicion4)

        var poderActualizado = poderNeoService.recuperar(poder5.id!!.toInt())

        Assertions.assertEquals(2, poderActualizado.condiciones.size)
        Assertions.assertEquals(poder1.id!!, condicion3.poderNeo?.id)
        Assertions.assertEquals(poder2.id!!, condicion4.poderNeo?.id)
    }

    @Test
    fun `el poder SuperFuerza puede mutar a Volar en el heroe superman`() {
        // Se agrega el contador de la condición 1 al héroe Superman
        var heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        heroeNeo.agregarCondicion(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 1
        heroeNeo.incrementarContador(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se vuelve a verificar que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 1
        // Quedaría la cantidad en 2, y ya la cumpliría
        heroeNeo.incrementarContador(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que el poder SuperFuerza puede mutar a Volar
        var poderesQuePuedenMutar = mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).toList()
        Assertions.assertEquals(1, poderesQuePuedenMutar.size)
        Assertions.assertEquals("SuperFuerza", poderesQuePuedenMutar.get(0).nombre)
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder1))
    }

    @Test
    fun `se agrega el poder RayosLaser al héroe y puede mutar a SuperVelocidad, y el poder SuperFuerza puede mutar a Volar en el heroe superman`() {
        // Se agrega el poder RayosLaser al héroe Superman
        // Al agregar el poder se agregan los contadores de las condiciones del poder
        var heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        var rayosLaserNeo = poderNeoService.recuperar(poder4.id!!.toInt())
        heroeNeo.agregarPoder(rayosLaserNeo)

        // Se agrega el contador de la condición 1 y 4 al héroe Superman
        heroeNeo.agregarCondicion(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 1 y 4
        heroeNeo.incrementarContador(condicion1)
        heroeNeo.incrementarContador(condicion4)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que únicamente el poder RayosLaser puede mutar a SuperVelocidad
        var poderesQuePuedenMutar = mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).toList()
        Assertions.assertEquals(1, poderesQuePuedenMutar.size)
        Assertions.assertEquals("RayosLaser", poderesQuePuedenMutar.get(0).nombre)
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder4))

        // Se incrementa el contador de la condición 1
        // Quedaria la cantidad en 2, y ya la cumpliría
        heroeNeo.incrementarContador(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que tanto el poder SuperFuerza puede mutar a Volar,
        // y el poder RayosLaser puede mutar a SuperVelocidad
        poderesQuePuedenMutar = mutacionService.poderesQuePuedenMutar(heroeNeo.id!!).toList()
        Assertions.assertEquals(2, poderesQuePuedenMutar.size)
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder1))
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder4))
    }

    @Test
    fun `el poder SuperFuerza esta habilitado a mutar a Volar en el héroe superman`() {
        // Se agrega el contador de la condición 1 al héroe Superman
        var heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        var poderNeo = poderNeoService.recuperar(poder1.id!!.toInt())
        heroeNeo.agregarCondicion(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 1
        heroeNeo.incrementarContador(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se vuelve a verificar que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 1
        // Quedaría la cantidad en 2, y ya la cumpliría
        heroeNeo.incrementarContador(condicion1)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que el poder SuperFuerza puede mutar a Volar
        var mutacionesHabilitadas = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(1, mutacionesHabilitadas.size)
        Assertions.assertEquals("Volar", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(mutacionesHabilitadas.contains(poder2))
    }

    @Test
    fun seAgregaElPoderVolarASupermanYPuedeMutarARapidezYRayosLaser() {
        // Se agrega el contador de la condición 2 y 3 al héroe Superman
        var heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        var poderNeo = poderNeoService.recuperar(poder2.id!!.toInt())
        heroeNeo.agregarPoder(poderNeo)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 2 y 3
        heroeNeo.incrementarContador(condicion2)
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningun poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 2 y 3
        heroeNeo.incrementarContador(condicion2)
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que únicamente el poder Volar puede mutar a Rapidez
        var mutacionesHabilitadas = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(1, mutacionesHabilitadas.size)
        Assertions.assertEquals("Rapidez", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(mutacionesHabilitadas.contains(poder3))

        // Se incrementa el contador de la condición 3
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que el poder Volar puede mutar a Rapidez y RayosLaser
        var poderesQuePuedenMutar = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(2, poderesQuePuedenMutar.size)
        Assertions.assertEquals("Rapidez", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder3))
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder4))
    }

    @Test
    fun seVerificaElCaminoMasRentable() {
        var atributos: Set<Atributo> = emptySet()
        //Se agregan los atributos Fuerza y Destreza a la colección
        atributos += fuerza
        atributos += destreza

        //se crean nuevas mutaciones
        mutacionService.crearMutacion(poder1.id!!, poder6.id!!, condicion7)
        mutacionService.crearMutacion(poder6.id!!, poder4.id!!, condicion8)
        mutacionService.crearMutacion(poder4.id!!, poder7.id!!, condicion6)
        mutacionService.crearMutacion(poder7.id!!, poder8.id!!, condicion5)
        mutacionService.crearMutacion(poder1.id!!, poder8.id!!, condicion10)

        //Se verifica el camino más rentable entre SuperFuerza y Rapidez según los atributos Fuerza y Destreza
        var caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder3.id!!, atributos)
        Assertions.assertEquals(3, caminoMasRentable.size)
        Assertions.assertTrue(caminoMasRentable.contains(poder1))
        Assertions.assertTrue(caminoMasRentable.contains(poder2))
        Assertions.assertTrue(caminoMasRentable.contains(poder3))

        //Se quita el atributo Destreza de la colección
        atributos -= destreza

        //Se verifica el camino más rentable entre SuperFuerza y Rapidez según el atributo Fuerza
        caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder3.id!!, atributos)
        Assertions.assertEquals(0, caminoMasRentable.size)
        Assertions.assertFalse(caminoMasRentable.contains(poder1))

        //Se agrega el atributo Destreza de la colección y se elimina Fuerza
        atributos += destreza
        atributos -= fuerza

        //Se verifica el camino más rentable entre SuperFuerza y control Sísmico según el atributo Destreza
        caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder8.id!!, atributos)
        Assertions.assertEquals(2, caminoMasRentable.size)
        Assertions.assertTrue(caminoMasRentable.contains(poder1))
        Assertions.assertTrue(caminoMasRentable.contains(poder8))

        //Se crea la mutación entre el poder Volar y el poder Sexto Sentido
        mutacionService.crearMutacion(poder2.id!!, poder9.id!!, condicion11)
        //Se crea la mutación entre el poder Control Sísmico y el poder Energía Cósmica
        mutacionService.crearMutacion(poder8.id!!, poder10.id!!, condicion12)
        //Se crea la mutación entre el poder Energía Cósmica y el poder Sexto Sentido
        mutacionService.crearMutacion(poder10.id!!, poder9.id!!, condicion13)

        //Se agrega el atributo Fuerza y se quita el atributo Destreza de la colección
        atributos += fuerza
        atributos -= destreza

        //Se verifica el camino más rentable entre SuperFuerza y Sexto Sentido según el atributo Fuerza
        caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder9.id!!, atributos)
        Assertions.assertEquals(3, caminoMasRentable.size)
        Assertions.assertTrue(caminoMasRentable.contains(poder1))
        Assertions.assertTrue(caminoMasRentable.contains(poder2))
        Assertions.assertTrue(caminoMasRentable.contains(poder9))

        //Se quita el atributo Fuerza de la colección
        atributos -= fuerza

        //Se verifica el camino más rentable entre SuperFuerza y Sexto Sentido sin ningún atributo
        caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder9.id!!, atributos)
        Assertions.assertEquals(0, caminoMasRentable.size)
        Assertions.assertFalse(caminoMasRentable.contains(poder1))
        Assertions.assertFalse(caminoMasRentable.contains(poder2))
        Assertions.assertFalse(caminoMasRentable.contains(poder9))
    }

    @Test
    fun noSePuedeVerificarElCaminoMasRentablePorQueUnosDeLosPoderesNoExisteTest() {
        var atributos: Set<Atributo> = emptySet()
        var idPoder: Long = 50
        try {
            mutacionService.caminoMasRentable(idPoder, poder9.id!!, atributos)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun noSePuedeVerificarElCaminoMasRentablePorQueLaBBDDEstaVaciaTest() {
        //Hago un clean y elimino todos los datos de la BBDD
        this.clean()
        var atributos: Set<Atributo> = emptySet()
        var todosLosPoderes = poderService.recuperarTodos()
        var todosLosHeroes = heroeService.recuperarTodos()

        //Verifico que no existen poderes ni heroes en la BBDD
        Assertions.assertTrue(todosLosPoderes.isEmpty())
        Assertions.assertTrue(todosLosHeroes.isEmpty())

        //Camino mas rentable falla porque se le pasan dos poderes que no existen
        try {
            var caminoMasRentable = mutacionService.caminoMasRentable(poder1.id!!, poder9.id!!, atributos)
        } catch (e: RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `se agrega el poder volar a superman y puede mutar a rapidez y rayosLaser pero al agregar Rapidez no puede mutar ya que es un poder propio`() {
        // Se agrega el contador de la condición 2 y 3 al héroe Superman
        var heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        var poderNeo = poderNeoService.recuperar(poder2.id!!.toInt())
        heroeNeo.agregarPoder(poderNeo)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningún poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 2 y 3
        heroeNeo.incrementarContador(condicion2)
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que ningun poder puede mutar
        Assertions.assertTrue(mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).isEmpty())

        // Se incrementa el contador de la condición 2 y 3
        heroeNeo.incrementarContador(condicion2)
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que únicamente el poder Volar puede mutar a Rapidez
        var mutacionesHabilitadas = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(1, mutacionesHabilitadas.size)
        Assertions.assertEquals("Rapidez", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(mutacionesHabilitadas.contains(poder3))

        // Se incrementa el contador de la condición 3
        heroeNeo.incrementarContador(condicion3)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que el poder Volar puede mutar a Rapidez y RayosLaser
        var poderesQuePuedenMutar = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(2, poderesQuePuedenMutar.size)
        Assertions.assertEquals("Rapidez", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder3))
        Assertions.assertTrue(poderesQuePuedenMutar.contains(poder4))

        // Se agrega el poder Rapidez al héroe Superman
        heroeNeo = heroeNeoService.recuperar(heroe.id!!.toInt())
        var poderNeoRapidez = poderNeoService.recuperar(poder3.id!!.toInt())
        heroeNeo.agregarPoder(poderNeoRapidez)
        heroeNeoService.actualizar(heroeNeo)

        // Se verifica que únicamente el poder Volar puede mutar a RayosLaser, ya que Rapidez es un poder del heroe
        mutacionesHabilitadas = mutacionService.mutacionesHabilitadas(heroeNeo.id!!, poderNeo.id!!).toList()
        Assertions.assertEquals(1, mutacionesHabilitadas.size)
        Assertions.assertEquals("RayosLaser", mutacionesHabilitadas.get(0).nombre)
        Assertions.assertTrue(mutacionesHabilitadas.contains(poder4))
    }

    @AfterEach
    fun clean(){
        heroeService.clear()
        poderService.clear()
    }
}