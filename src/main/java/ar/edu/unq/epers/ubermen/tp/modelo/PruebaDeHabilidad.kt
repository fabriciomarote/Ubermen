package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import javax.persistence.*

@Entity
class PruebaDeHabilidad() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre:String? = null
    var dificultad: Int = 0
    var cantidadDeExitos: Int = 0
    var retalacion: Int = 0
    var atributoDesafiado: Atributo? = null

    constructor(nombre: String, dificultad: Int, cantidadDeExitos: Int, retalacion: Int, atributoDesafiado: Atributo):this() {
        this.nombre = nombre
        this.dificultad = dificultad
        this.cantidadDeExitos = cantidadDeExitos
        this.retalacion = retalacion
        this.atributoDesafiado = atributoDesafiado
    }

    constructor(nombre: String): this() {
        this.nombre = nombre
    }

    fun cambiarNombre(nombreNuevo: String) {
        if(nombreNuevo == "") {
            throw StringVacioException()
        }
        this.nombre = nombreNuevo
    }

    fun cambiarDificultad(dificultad: Int) {
        if(dificultad < 0) {
            throw CantidadNegativaException()
        } else if (dificultad > 6) {
            throw RuntimeException("La dificultad no debe ser mayor a 6.")
        }
        this.dificultad = dificultad
    }

    fun cambiarCantidadDeExitos(exitos: Int) {
        if(exitos < 0) {
            throw CantidadNegativaException()
        }
        this.cantidadDeExitos = exitos
    }

    fun sumarCantidadDeExitos(cant: Int) {
        if(cant < 0) {
            throw CantidadNegativaException()
        }
        this.cantidadDeExitos += cant
    }

    fun cambiarRetalacion(retalacion: Int) {
        if(retalacion < 0) {
            throw CantidadNegativaException()
        } else {
            this.retalacion = retalacion
        }
    }

    fun cambiarAtributoDesafiado(atributo: Atributo) {
        if(atributo == null) {
            throw AtributoVacioException()
        }
        this.atributoDesafiado = atributo
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PruebaDeHabilidad

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (dificultad != other.dificultad) return false
        if (cantidadDeExitos != other.cantidadDeExitos) return false
        if (retalacion != other.retalacion) return false
        if (atributoDesafiado != other.atributoDesafiado) return false

        return true
    }
}