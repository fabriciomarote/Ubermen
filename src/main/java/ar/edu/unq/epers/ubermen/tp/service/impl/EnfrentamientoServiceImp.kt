package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.modelo.*
import ar.edu.unq.epers.ubermen.tp.service.*
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.AtributoInvalidoException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.NoHayConflictosException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class EnfrentamientoServiceImp (
    private val heroeService : HeroeService,
    private val heroeMongoService: HeroeMongoService,
    private val conflictoService : ConflictoService,
    private val conflictoMongoService : ConflictoMongoService
) : EnfrentamientoService {

    lateinit var dado: Dado;

    override fun obtenerConflictoAleatorio(idHeroe: Long): Conflicto {
        // Si el id del heroe no existe levanta excepcion
        var heroe = heroeService.recuperar(idHeroe.toInt())
        var heroeMongo : HeroeMongo = heroeMongoService.recuperar(idHeroe)
        if(conflictoService.recuperarTodos().isEmpty()) {
            throw NoHayConflictosException()
        } else {
            var conflictoMongoAleatorio = conflictoMongoService.obtenerConflictoAleatorio(heroeMongo)
            var conflictoAleatorio = conflictoService.recuperar(conflictoMongoAleatorio.idConflicto!!.toInt())!!
            return conflictoAleatorio
        }
    }

    override fun enfrentar(heroeId: Int, conflictoId: Int): Conflicto {
        var heroe = heroeService.recuperar(heroeId)
        var conflicto = conflictoService.recuperar(conflictoId)

        for (prueba in conflicto!!.pruebas){
            if (heroe!!.estaMuerto()){
                break
            }
            var exito : Int = 0
            repeat(puntosDeAtributoDesafiadoDeHeroe(heroe, prueba.atributoDesafiado)){
                var resultado = dado.tirarDado();
                exito += if (resultado >= prueba.dificultad) 1 else 0
            }
            if (exito >= prueba.cantidadDeExitos){
                print("prueba superada")
                puntosDeProgreso(conflicto, heroe, prueba)
                var conflictoResuelto = conflicto.progresoHaciaSuResolucion >= 10
                if (conflictoResuelto){
                    heroeService.resolverConflicto(heroeId, conflictoId)
                    break
                }
                conflictoService.actualizar(conflicto)
            } else {
                heroe.restarVida(prueba.retalacion)
                heroeService.actualizar(heroe)
                if (heroe!!.estaMuerto()){
                    conflicto.reiniciarProgreso()
                    conflictoService.actualizar(conflicto)
                    break
                }
            }
        }

        return conflicto!!
    }

    fun puntosDeProgreso(conflicto: Conflicto, heroe: Heroe, prueba: PruebaDeHabilidad) {
        var cantidadExitos = prueba.cantidadDeExitos
        var esPoderFavorable = false
        for (poderHeroe in heroe.poderes){
            for (poderConflicto in conflicto.poderesFavorables){
                if (poderHeroe.equals(poderConflicto)) {
                    esPoderFavorable = true
                }
            }
        }
        if (esPoderFavorable) {
            cantidadExitos = cantidadExitos * 2
        }
        conflicto.incrementarProgreso(cantidadExitos)
    }

    fun puntosDeAtributoDesafiadoDeHeroe(heroe: Heroe, atributoDesafiado: Atributo?): Int {
        var puntosAtributo: Int = 0
        when(atributoDesafiado) {
            Atributo.FUERZA-> puntosAtributo = heroe.getFuerza()
            Atributo.DESTREZA -> puntosAtributo = heroe.getDestreza()
            Atributo.CONSTITUCION -> puntosAtributo = heroe.getConstitucion()
            Atributo.INTELIGENCIA -> puntosAtributo = heroe.getInteligencia()
            else -> {
                throw AtributoInvalidoException()
            }
        }
        return puntosAtributo
    }

    override fun clear() {
        heroeService.clear()
        conflictoService.clear()
    }
}