package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.persistence.ConflictoDAO
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
open class ConflictoServiceImp (
    @Autowired
    private val conflictoDAO: ConflictoDAO,
    private val villanoService: VillanoService,
    private val pruebaDeHabilidadService: PruebaDeHabilidadService,
    private val conflictoMongoService : ConflictoMongoService,
    private val villanoMongoService : VillanoMongoService,
    private val distritoMongoService: DistritoService
) : ConflictoService {

    override fun crear(conflicto: Conflicto): Conflicto {
        this.validarNombre(conflicto)
        return conflictoDAO.save(conflicto)
    }

    private fun validarNombre(conflicto: Conflicto) {
        if(conflicto.nombre == "") {
            throw StringVacioException()
        }
    }

    override fun actualizar(conflicto: Conflicto): Conflicto {
        var conflictoActualizado = this.crear(conflicto)
        var conflictoMongo = conflictoMongoService.recuperar(conflicto.id!!)

        conflictoMongo.resuelto = conflicto.resueltoPor != null
        conflictoMongoService.actualizar(conflictoMongo)

        return conflictoActualizado
    }

    override fun recuperar(conflictoId:Int): Conflicto {
        val conflicto = conflictoDAO.findByIdOrNull(conflictoId.toLong())
        if (conflicto == null) {
            throw RuntimeException("El id [${conflictoId}] no existe.")
        }
        return conflicto
    }

    override fun eliminar(conflictoId:Int) {
        this.recuperar(conflictoId)
        conflictoDAO.deleteById(conflictoId.toLong())
        conflictoMongoService.eliminar(conflictoId.toLong())
    }

    override fun recuperarTodos():List<Conflicto> {
        return conflictoDAO.findAllByOrderByNombreAsc()
    }

    override fun crearConflicto(villanoID:Int, nombre:String) : Conflicto {
        val villano = villanoService.recuperar(villanoID)
        var conflicto = villano.creacionConflicto(nombre)
        val poderesVillano = villano.poderes
        for(poder in poderesVillano) {
            crearPruebaDeHabilidad(conflicto, poder)
        }
        // Se crea en el ConflictoDAO
        this.crear(conflicto)
        // Se crea en el ConflictoMongoDAO
        val villanoMongo = villanoMongoService.recuperar(villanoID.toLong())
        var conflictoMongo = ConflictoMongo(conflicto.id!!, villanoMongo.coordenada!!, villanoMongo.idDistrito!!)
        conflictoMongoService.crear(conflictoMongo)
        villanoService.actualizar(villano)
        return conflicto
    }

    fun crearPruebaDeHabilidad(conflicto: Conflicto, poder: Poder) {
        val pruebaDeHabilidad = PruebaDeHabilidad()
        pruebaDeHabilidad.cambiarAtributoDesafiado(atributoDesafiadoPrueba(pruebaDeHabilidad, poder))
        asignacionAleatoriaDeCantidadPotenciada(poder, pruebaDeHabilidad)
        asignacionAleatoriaDeNombre(poder, pruebaDeHabilidad)
        pruebaDeHabilidadService.crear(pruebaDeHabilidad)
        conflicto.agregarPrueba(pruebaDeHabilidad)
    }

    fun atributoDesafiadoPrueba(prueba: PruebaDeHabilidad, poder: Poder): Atributo {
        var atributoDesafiado: Atributo = Atributo.FUERZA //REVISAR
        when(poder.atributoPotenciado){
            Atributo.DESTREZA -> atributoDesafiado = Atributo.CONSTITUCION
            Atributo.FUERZA -> atributoDesafiado = Atributo.INTELIGENCIA
            Atributo.CONSTITUCION -> atributoDesafiado = Atributo.FUERZA
            Atributo.INTELIGENCIA -> atributoDesafiado = Atributo.DESTREZA
        }
        return atributoDesafiado
    }

    fun asignacionAleatoriaDeNombre(poder: Poder, prueba: PruebaDeHabilidad) {
        val nombreAleatorio = GeneradorRandom.valorRandom(poder.nombresPosiblesDePruebasDehabilidad)
        prueba.cambiarNombre(nombreAleatorio)
    }

    fun asignacionAleatoriaDeCantidadPotenciada(poder: Poder, prueba: PruebaDeHabilidad) {
        var cantidadPotenciada = poder.cantidadPotenciada
        prueba.dificultad = asignacionAleatoriaDificultad(cantidadPotenciada)
        cantidadPotenciada-= prueba.dificultad
        prueba.cantidadDeExitos = GeneradorRandom.valorRandom(cantidadPotenciada)
        cantidadPotenciada-= prueba.cantidadDeExitos
        prueba.retalacion = cantidadPotenciada
    }

    fun asignacionAleatoriaDificultad(cantidadPotenciada: Int): Int {
        var dificultad = 0
        do {
            dificultad = GeneradorRandom.valorRandom(cantidadPotenciada)
        } while(dificultad > 6)

        return dificultad
    }

    override fun clear() {
        conflictoDAO.deleteAll()
        conflictoMongoService.clear()
    }
}