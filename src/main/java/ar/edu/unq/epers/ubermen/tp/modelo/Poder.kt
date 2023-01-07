package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import javax.persistence.*

@Entity
class Poder () {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre: String? = null
    var atributoPotenciado: Atributo? = null
    var cantidadPotenciada: Int = 0
    @ElementCollection(fetch = FetchType.EAGER)
    var nombresPosiblesDePruebasDehabilidad: MutableList<String> = mutableListOf<String>()

    constructor(nombre: String):this() {
        this.nombre = nombre
    }

    constructor(nombre: String, atributo: Atributo, cantidadPotenciada: Int):this() {
        this.nombre = nombre
        this.atributoPotenciado = atributo
        this.cantidadPotenciada = cantidadPotenciada
    }

    constructor(nombre: String, atributo: Atributo, cantidadPotenciada: Int, nombresPosiblesDePruebasDehabilidad: MutableList<String>):this() {
        this.nombre = nombre
        this.atributoPotenciado = atributo
        this.cantidadPotenciada = cantidadPotenciada
        this.nombresPosiblesDePruebasDehabilidad = nombresPosiblesDePruebasDehabilidad
    }

    fun cambiarCantidadPotenciada(cant: Int) {
        if(cant < 0) {
            throw CantidadNegativaException()
        }
        this.cantidadPotenciada = cant
    }

    fun cambiarNombre(nombreNuevo: String) {
        if(nombreNuevo == "") {
            throw StringVacioException()
        }
        this.nombre = nombreNuevo
    }

    fun cambiarAtributoPotenciado(atributo: Atributo) {
        if(atributo == null) {
            throw AtributoVacioException()
        }
        this.atributoPotenciado = atributo
    }

    fun agregarNombrePosible(nombrePosible: String) {
        this.nombresPosiblesDePruebasDehabilidad.add(nombrePosible)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Poder

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (atributoPotenciado != other.atributoPotenciado) return false
        if (cantidadPotenciada != other.cantidadPotenciada) return false
        return true
    }
}