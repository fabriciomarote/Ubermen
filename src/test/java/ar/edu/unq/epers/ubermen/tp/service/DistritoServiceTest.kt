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
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

@SpringBootTest
class DistritoServiceTest {
    @Autowired
    lateinit var distritoDAO : DistritoDAO
    @Autowired
    lateinit var heroeCoordenadaDAO : HeroeCoordenadaDAO
    @Autowired
    lateinit var conflictoCoordenadaDAO : ConflictoCoordenadaDAO

    lateinit var distritoService: DistritoService
    lateinit var heroeMongoService: HeroeMongoService
    lateinit var conflictoMongoService: ConflictoMongoService

    lateinit var bernal: Distrito
    lateinit var wilde: Distrito
    lateinit var ezpeleta: Distrito
    lateinit var monteChigolo: Distrito
    lateinit var sanJose: Distrito
    lateinit var villaEspania: Distrito

    lateinit var conflicto1 : ConflictoMongo
    lateinit var conflicto2 : ConflictoMongo
    lateinit var conflicto3 : ConflictoMongo
    lateinit var conflicto4 : ConflictoMongo
    lateinit var conflicto5 : ConflictoMongo
    lateinit var conflicto6 : ConflictoMongo
    lateinit var conflicto7 : ConflictoMongo
    lateinit var conflicto8 : ConflictoMongo
    lateinit var conflicto9 : ConflictoMongo
    lateinit var conflicto10 : ConflictoMongo
    lateinit var conflicto11 : ConflictoMongo
    lateinit var conflicto12 : ConflictoMongo
    lateinit var conflicto13 : ConflictoMongo
    lateinit var conflicto14 : ConflictoMongo
    lateinit var conflicto15 : ConflictoMongo

    lateinit var heroeMongo1 : HeroeMongo
    lateinit var heroeMongo2 : HeroeMongo
    lateinit var heroeMongo3 : HeroeMongo
    lateinit var heroeMongo4 : HeroeMongo
    lateinit var heroeMongo5 : HeroeMongo
    lateinit var heroeMongo6 : HeroeMongo
    lateinit var heroeMongo7 : HeroeMongo
    lateinit var heroeMongo8 : HeroeMongo
    lateinit var heroeMongo9 : HeroeMongo
    lateinit var heroeMongo10 : HeroeMongo

    lateinit var coordenadaHeroe1 : GeoJsonPoint
    lateinit var coordenadaHeroe2 : GeoJsonPoint
    lateinit var coordenadaHeroe3 : GeoJsonPoint
    lateinit var coordenadaHeroe4 : GeoJsonPoint
    lateinit var coordenadaHeroe5 : GeoJsonPoint
    lateinit var coordenadaHeroe6 : GeoJsonPoint
    lateinit var coordenadaHeroe7 : GeoJsonPoint
    lateinit var coordenadaHeroe8 : GeoJsonPoint
    lateinit var coordenadaHeroe9 : GeoJsonPoint
    lateinit var coordenadaHeroe10 : GeoJsonPoint

    lateinit var coordenadaConflicto1 : GeoJsonPoint
    lateinit var coordenadaConflicto2 : GeoJsonPoint
    lateinit var coordenadaConflicto3 : GeoJsonPoint
    lateinit var coordenadaConflicto4 : GeoJsonPoint
    lateinit var coordenadaConflicto5 : GeoJsonPoint
    lateinit var coordenadaConflicto6 : GeoJsonPoint
    lateinit var coordenadaConflicto7 : GeoJsonPoint
    lateinit var coordenadaConflicto8 : GeoJsonPoint
    lateinit var coordenadaConflicto9 : GeoJsonPoint
    lateinit var coordenadaConflicto10 : GeoJsonPoint
    lateinit var coordenadaConflicto11 : GeoJsonPoint
    lateinit var coordenadaConflicto12 : GeoJsonPoint
    lateinit var coordenadaConflicto13 : GeoJsonPoint
    lateinit var coordenadaConflicto14 : GeoJsonPoint
    lateinit var coordenadaConflicto15 : GeoJsonPoint

    lateinit var coordenadasBernal: MutableList<GeoJsonPoint>
    lateinit var coordenadasWilde: MutableList<GeoJsonPoint>
    lateinit var coordenadasEzpeleta: MutableList<GeoJsonPoint>
    lateinit var coordenadasMonteChingolo: MutableList<GeoJsonPoint>
    lateinit var coordenadasSanJose: MutableList<GeoJsonPoint>
    lateinit var coordenadasVillaEspania: MutableList<GeoJsonPoint>

    lateinit var coordenada1: GeoJsonPoint
    lateinit var coordenada2: GeoJsonPoint
    lateinit var coordenada3: GeoJsonPoint
    lateinit var coordenada4: GeoJsonPoint
    lateinit var coordenada5: GeoJsonPoint
    lateinit var coordenada6: GeoJsonPoint
    lateinit var coordenada7: GeoJsonPoint
    lateinit var coordenada8: GeoJsonPoint
    lateinit var coordenada9: GeoJsonPoint
    lateinit var coordenada10: GeoJsonPoint
    lateinit var coordenada11: GeoJsonPoint
    lateinit var coordenada12: GeoJsonPoint
    lateinit var coordenada13: GeoJsonPoint
    lateinit var coordenada14: GeoJsonPoint
    lateinit var coordenada15: GeoJsonPoint
    lateinit var coordenada16: GeoJsonPoint
    lateinit var coordenada17: GeoJsonPoint
    lateinit var coordenada18: GeoJsonPoint
    lateinit var coordenada19: GeoJsonPoint
    lateinit var coordenada20: GeoJsonPoint
    lateinit var coordenada21: GeoJsonPoint
    lateinit var coordenada22: GeoJsonPoint
    lateinit var coordenada23: GeoJsonPoint
    lateinit var coordenada24: GeoJsonPoint
    lateinit var coordenada25: GeoJsonPoint
    lateinit var coordenada26: GeoJsonPoint
    lateinit var coordenada27: GeoJsonPoint
    lateinit var coordenada28: GeoJsonPoint

    @BeforeEach
    fun prepare() {
        this.heroeMongoService = HeroeMongoServiceImp(heroeCoordenadaDAO)
        this.conflictoMongoService = ConflictoMongoServiceImp(conflictoCoordenadaDAO)
        this.distritoService = DistritoServiceImp(distritoDAO, heroeMongoService, conflictoMongoService)

        //Coordenadas de Bernal
        coordenada1 = GeoJsonPoint(-34.70994488483138, -58.26176537112486)
        coordenada2 = GeoJsonPoint(-34.70034861628726,  -58.28545464149768)
        coordenada3 = GeoJsonPoint(-34.713754859185414, -58.30167664186167)
        coordenada4 = GeoJsonPoint(-34.72176274424697, -58.28543392774067)
        coordenada5 = GeoJsonPoint(-34.70994488483138, -58.26176537112486)

        //Coordenadas de Wilde
        coordenada6 = GeoJsonPoint(-34.69512504462424, -58.32357509151246)
        coordenada7 = GeoJsonPoint(-34.70262686563159, -58.3077669013321)
        coordenada8 = GeoJsonPoint(-34.726640388181636, -58.33347365420234)
        coordenada9 = GeoJsonPoint(-34.7142347571214, -58.34664884111273)
        coordenada10 = GeoJsonPoint(-34.69512504462424, -58.32357509151246)

        //Coordenadas de Ezpeleta
        coordenada11 = GeoJsonPoint(-34.753786407491425, -58.261804481640354)
        coordenada12 = GeoJsonPoint(-34.765434080776195, -58.25163485347676)
        coordenada13 = GeoJsonPoint(-34.77329655970343, -58.267308045117126)
        coordenada14 = GeoJsonPoint(-34.76209230042485, -58.27675981717505)
        coordenada15 = GeoJsonPoint(-34.753786407491425, -58.261804481640354)

        //Coordenadas de Monte Chingolo
        coordenada16 = GeoJsonPoint(-34.73980108369873, -58.347787935374605)
        coordenada17 = GeoJsonPoint(-34.7254099286142, -58.36604974507952)
        coordenada18 = GeoJsonPoint(-34.71238720058725, -58.35095665121382)
        coordenada19 = GeoJsonPoint(-34.727054758881664, -58.33369548861604)
        coordenada20 = GeoJsonPoint(-34.73980108369873, -58.347787935374605)

        //Coordenadas de San Jose
        coordenada21 = GeoJsonPoint(-34.75439119498883, -58.33680563768631)
        coordenada22 = GeoJsonPoint(-34.7645255324514, -58.32931438948289)
        coordenada23 = GeoJsonPoint(-34.77720196004079, -58.35313655876979)
        coordenada24 = GeoJsonPoint(-34.75439119498883, -58.33680563768631)

        //Coordenadas de Villa España
        coordenada25 = GeoJsonPoint(-34.76812738237791, -58.19452601250508)
        coordenada26 = GeoJsonPoint(-34.77397925914421, -58.18744498060016)
        coordenada27 = GeoJsonPoint(-34.78226283296864, -58.20388155768855)
        coordenada28 = GeoJsonPoint(-34.76812738237791, -58.19452601250508)

        coordenadasBernal = mutableListOf(coordenada1, coordenada2, coordenada3, coordenada4, coordenada5)
        coordenadasWilde = mutableListOf(coordenada6, coordenada7, coordenada8, coordenada9, coordenada10)
        coordenadasEzpeleta = mutableListOf(coordenada11, coordenada12, coordenada13, coordenada14, coordenada15)
        coordenadasMonteChingolo = mutableListOf(coordenada16, coordenada17, coordenada18, coordenada19, coordenada20)
        coordenadasSanJose = mutableListOf(coordenada21, coordenada22, coordenada23, coordenada24)
        coordenadasVillaEspania = mutableListOf(coordenada25, coordenada26, coordenada27, coordenada28)

        //Heroes en distrito Bernal
        coordenadaHeroe1 = GeoJsonPoint(-34.7033175347906, -58.282708692799915)
        coordenadaHeroe2 = GeoJsonPoint(-34.71662984275966, -58.28750960960643)
        coordenadaHeroe3 = GeoJsonPoint(-34.710091764540486, -58.26608462266394)

        //Heroes en distrito Wilde
        coordenadaHeroe4 = GeoJsonPoint(-34.70968723481812, -58.33626858253664)

        //Heroes en distrito Ezpeleta
        coordenadaHeroe5 = GeoJsonPoint(-34.759332220036335, -58.26367640599186)
        coordenadaHeroe6 = GeoJsonPoint(-34.768688220711546, -58.266202653221136)

        //Heroes en distrito Monte Chingolo
        coordenadaHeroe7 = GeoJsonPoint(-34.71918827257485, -58.3524470900249)
        coordenadaHeroe8 = GeoJsonPoint(-34.72717859317951, -58.3424014299422)
        coordenadaHeroe9 = GeoJsonPoint(-34.72972129037002, -58.3561144363823)
        coordenadaHeroe10 = GeoJsonPoint(-34.73639382158578, -58.3474573232919)

        //Conflictos en distrito Bernal
        coordenadaConflicto1 = GeoJsonPoint(-34.70867805804999, -58.27905426358899)
        coordenadaConflicto2 = GeoJsonPoint(-34.71433273903931, -58.297183098694184)
        coordenadaConflicto3 = GeoJsonPoint(-34.71698323766866, -58.28077399498237)

        //Conflictos en distrito Wilde
        coordenadaConflicto4 = GeoJsonPoint(-34.717923387247545, -58.33232985619158)
        coordenadaConflicto5 = GeoJsonPoint(-34.710141664605906, -58.32161928455147)
        coordenadaConflicto6 = GeoJsonPoint(-34.704006651825416, -58.33122424879646)
        coordenadaConflicto7 = GeoJsonPoint(-34.70474514967317, -58.31208342076866)

        //Conflictos en distrito Ezpeleta
        coordenadaConflicto8 = GeoJsonPoint(-34.76637483559524, -58.25535221430194)
        coordenadaConflicto9 = GeoJsonPoint(-34.757733088365704, -58.2640905448819)
        coordenadaConflicto10 = GeoJsonPoint(-34.76086327452726, -58.27195918379278)
        coordenadaConflicto11 = GeoJsonPoint(-34.76783771901472, -58.26926728100748)

        //Conflictos en distrito Monte Chingolo
        coordenadaConflicto12 = GeoJsonPoint(-34.72027683635331, -58.345577702513886)
        coordenadaConflicto13 = GeoJsonPoint(-34.73315725996354, -58.3417931492171)
        coordenadaConflicto14 = GeoJsonPoint(-34.73064513675319, -58.34897409649818)
        coordenadaConflicto15 = GeoJsonPoint(-34.7183625463922, -58.35154565194343)

        bernal = Distrito("Distrito1", coordenadasBernal)
        wilde = Distrito("Distrito2", coordenadasWilde)
        ezpeleta = Distrito("Distrito3", coordenadasEzpeleta)
        monteChigolo = Distrito("Distrito4", coordenadasMonteChingolo)
        sanJose = Distrito("Distrito5", coordenadasSanJose)
        villaEspania = Distrito("Distrito6", coordenadasVillaEspania)

        //se persisten los distritos
        distritoService.crear(bernal)
        distritoService.crear(wilde)
        distritoService.crear(ezpeleta)
        distritoService.crear(monteChigolo)

        heroeMongo1 = HeroeMongo(1, coordenadaHeroe1, distritoService.distritoDe(coordenadaHeroe1))
        heroeMongo2 = HeroeMongo(2, coordenadaHeroe2, distritoService.distritoDe(coordenadaHeroe2))
        heroeMongo3 = HeroeMongo(3, coordenadaHeroe3, distritoService.distritoDe(coordenadaHeroe3))
        heroeMongo4 = HeroeMongo(4, coordenadaHeroe4, distritoService.distritoDe(coordenadaHeroe4))
        heroeMongo5 = HeroeMongo(5, coordenadaHeroe5, distritoService.distritoDe(coordenadaHeroe5))
        heroeMongo6 = HeroeMongo(6, coordenadaHeroe6, distritoService.distritoDe(coordenadaHeroe6))
        heroeMongo7 = HeroeMongo(7, coordenadaHeroe7, distritoService.distritoDe(coordenadaHeroe7))
        heroeMongo8 = HeroeMongo(8, coordenadaHeroe8, distritoService.distritoDe(coordenadaHeroe8))
        heroeMongo9 = HeroeMongo(9, coordenadaHeroe9, distritoService.distritoDe(coordenadaHeroe9))
        heroeMongo10 = HeroeMongo(10, coordenadaHeroe10, distritoService.distritoDe(coordenadaHeroe10))

        conflicto1 = ConflictoMongo(1, coordenadaConflicto1, bernal.id!!)
        conflicto2 = ConflictoMongo(2, coordenadaConflicto2, bernal.id!!)
        conflicto3 = ConflictoMongo(3, coordenadaConflicto3, bernal.id!!)
        conflicto4 = ConflictoMongo(4, coordenadaConflicto4, wilde.id!!)
        conflicto5 = ConflictoMongo(5, coordenadaConflicto5, wilde.id!!)
        conflicto6 = ConflictoMongo(6, coordenadaConflicto6, wilde.id!!)
        conflicto7 = ConflictoMongo(7, coordenadaConflicto7, wilde.id!!)
        conflicto8 = ConflictoMongo(8, coordenadaConflicto8, ezpeleta.id!!)
        conflicto9 = ConflictoMongo(9, coordenadaConflicto9, ezpeleta.id!!)
        conflicto10 = ConflictoMongo(10, coordenadaConflicto10, ezpeleta.id!!)
        conflicto11 = ConflictoMongo(11, coordenadaConflicto11, ezpeleta.id!!)
        conflicto12 = ConflictoMongo(12, coordenadaConflicto12, monteChigolo.id!!)
        conflicto13 = ConflictoMongo(13, coordenadaConflicto13, monteChigolo.id!!)
        conflicto14 = ConflictoMongo(14, coordenadaConflicto14, monteChigolo.id!!)
        conflicto15 = ConflictoMongo(15, coordenadaConflicto15, monteChigolo.id!!)

        //se persisten los conflictoMongo
        conflictoMongoService.crear(conflicto1)
        conflictoMongoService.crear(conflicto2)
        conflictoMongoService.crear(conflicto3)
        conflictoMongoService.crear(conflicto4)
        conflictoMongoService.crear(conflicto5)
        conflictoMongoService.crear(conflicto6)
        conflictoMongoService.crear(conflicto7)
        conflictoMongoService.crear(conflicto8)
        conflictoMongoService.crear(conflicto9)
        conflictoMongoService.crear(conflicto10)
        conflictoMongoService.crear(conflicto11)
        conflictoMongoService.crear(conflicto12)
        conflictoMongoService.crear(conflicto13)
        conflictoMongoService.crear(conflicto14)
        conflictoMongoService.crear(conflicto15)

        //se persisten los HeroeMongo
        heroeMongoService.crear(heroeMongo1)
        heroeMongoService.crear(heroeMongo2)
        heroeMongoService.crear(heroeMongo3)
        heroeMongoService.crear(heroeMongo4)
        heroeMongoService.crear(heroeMongo5)
        heroeMongoService.crear(heroeMongo6)
        heroeMongoService.crear(heroeMongo7)
        heroeMongoService.crear(heroeMongo8)
        heroeMongoService.crear(heroeMongo9)
        heroeMongoService.crear(heroeMongo10)

        //se actualizan los conflictos que han sido resueltos por heroes
        var conflicto3Recuperado = conflictoMongoService.recuperar(conflicto3.idConflicto!!)
        conflicto3Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto3Recuperado)
        var conflicto7Recuperado = conflictoMongoService.recuperar(conflicto7.idConflicto!!)
        conflicto7Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto7Recuperado)
        var conflicto9Recuperado = conflictoMongoService.recuperar(conflicto9.idConflicto!!)
        conflicto9Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto9Recuperado)
        var conflicto11Recuperado = conflictoMongoService.recuperar(conflicto11.idConflicto!!)
        conflicto11Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto11Recuperado)
        var conflicto13Recuperado = conflictoMongoService.recuperar(conflicto13.idConflicto!!)
        conflicto13Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto13Recuperado)
        var conflicto14Recuperado = conflictoMongoService.recuperar(conflicto14.idConflicto!!)
        conflicto14Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto14Recuperado)
        var conflicto15Recuperado = conflictoMongoService.recuperar(conflicto15.idConflicto!!)
        conflicto15Recuperado.resuelto = true
        conflictoMongoService.actualizar(conflicto15Recuperado)
    }

    @Test
    fun `se crean 2 distritos, se recuperan uno por uno y luego todos`() {
        //se recuperan los distritos ya persistidos
        var distritosRecuperados = distritoService.recuperarTodos()
        Assertions.assertEquals(4, distritosRecuperados.size)
        Assertions.assertFalse(distritosRecuperados.contains(sanJose))
        Assertions.assertFalse(distritosRecuperados.contains(villaEspania))

        //se crean los distritos 5 y 6
        distritoService.crear(sanJose)
        distritoService.crear(villaEspania)

        //se recuperan los distritos nuevamente, con los distritos agregados
        distritosRecuperados = distritoService.recuperarTodos()
        Assertions.assertEquals(6, distritosRecuperados.size)
        Assertions.assertTrue(distritosRecuperados.contains(sanJose))
        Assertions.assertTrue(distritosRecuperados.contains(villaEspania))

        //se recuperan ambos distritos por separado
        val sanJoseRecuperado = distritoService.recuperar(sanJose.id!!)
        val villaEspaniaRecuperado = distritoService.recuperar(villaEspania.id!!)
        Assertions.assertEquals(sanJoseRecuperado, sanJose)
        Assertions.assertEquals(villaEspaniaRecuperado, villaEspania)
        Assertions.assertEquals(4, sanJoseRecuperado.coordenadas!!.points.size)
        Assertions.assertEquals(4, villaEspaniaRecuperado.coordenadas!!.points.size)
    }

    @Test
    fun `no se puede recuperar el distrito con id 20 porque no existe`() {
        val id = "20"
        try {
            distritoService.recuperar(id)
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `no se puede eliminar el distrito con id 40 porque no existe`() {
        val id = "40"
        try {
            distritoService.eliminar(id)
        } catch (e : RuntimeException) {
            Assertions.assertEquals(e.message, "El id no existe.")
        }
    }

    @Test
    fun `se verifica que distrito2 se elimina`() {
        //se recuperan todos los distritos
        var distritosRecuperados = distritoService.recuperarTodos()

        //se verifica que sean 4 los distritos persistidos
        Assertions.assertEquals(4, distritosRecuperados.size)
        Assertions.assertTrue(distritosRecuperados.contains(wilde))

        // Se elimina el distrito2 y se observa que el recuperar todos devuelve 3 y que no contiene al mismo
        distritoService.eliminar(wilde.id!!)
        distritosRecuperados = distritoService.recuperarTodos()
        Assertions.assertEquals(3, distritosRecuperados.size)
        Assertions.assertFalse(distritosRecuperados.contains(wilde))
    }

    @Test
    fun `se verifica que el distritos mas picante es Wilde`() {
        //se recuperan todos los distritos
        var distritosRecuperados = distritoService.recuperarTodos()
        //se verifica que sean 4 los distritos persistidos
        Assertions.assertEquals(4, distritosRecuperados.size)

        //se pide el distrito mas picante y se verifica que es Wilde porque tiene 3 conflictos sin resolver
        var masPicante = distritoService.masPicante()
        var wildeRecuperado = distritoService.recuperar(wilde.id!!)

        Assertions.assertEquals(wildeRecuperado, masPicante)
    }

    @Test
    fun`se verifica que el distrito mas vigilado es Monte Chingolo`() {
        //se recuperan todos los distritos
        var distritosRecuperados = distritoService.recuperarTodos()
        //se verifica que sean 4 los distritos persistidos
        Assertions.assertEquals(4, distritosRecuperados.size)

        //se pide el distrito mas picante y se verifica que es MonteChingolo porque tiene 4 heroes patrullando
        var masVigilado = distritoService.masVigilado()
        var monteChingoloRecuperado = distritoService.recuperar(monteChigolo.id!!)

        println(monteChingoloRecuperado.nombre)
        println(masVigilado.nombre)
        Assertions.assertEquals(monteChingoloRecuperado, masVigilado)
    }

    @AfterEach
    fun clear() {
        conflictoMongoService.clear()
        heroeMongoService.clear()
        distritoService.clear()
    }
}