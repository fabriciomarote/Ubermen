package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.ValorMayorException
import javax.persistence.*

@Entity
class Conflicto() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre: String? = null
    var progresoHaciaSuResolucion: Int = 0
    @OneToMany(fetch = FetchType.EAGER)
    var pruebas: MutableSet<PruebaDeHabilidad> = HashSet()
    @ManyToMany(fetch = FetchType.EAGER)
    var poderesFavorables: MutableSet<Poder> = HashSet()

    @ManyToOne
    var resueltoPor: Heroe? = null

    @ManyToOne
    var creadoPor: Villano? = null
    constructor(nombre: String):this() {
        this.nombre = nombre
    }

    fun cambiarNombre(nombreNuevo : String) {
        if(nombreNuevo == "") {
            throw StringVacioException()
        }
        this.nombre = nombreNuevo
    }

    fun incrementarProgreso(incremento : Int) {
        var resultado = this.progresoHaciaSuResolucion + incremento
        if(incremento < 0) {
            throw CantidadNegativaException()
        }
        if(this.progresoHaciaSuResolucion + incremento > 10) {
            resultado = 10
        }
        this.progresoHaciaSuResolucion = resultado
    }

    fun reiniciarProgreso() {
        this.progresoHaciaSuResolucion = 0
    }

    fun cambiarProgreso(nuevoProgreso: Int) {
        if(nuevoProgreso < 0) {
            throw CantidadNegativaException()
        } else if (nuevoProgreso > 10) {
            throw ValorMayorException()
        }
        this.progresoHaciaSuResolucion = nuevoProgreso
    }

    fun agregarPrueba(prueba : PruebaDeHabilidad) {
        this.pruebas.add(prueba)
    }

    fun eliminarPrueba(prueba : PruebaDeHabilidad) {
        this.pruebas.remove(prueba)
    }

    fun agregarPoderFavorable(poder : Poder) {
        this.poderesFavorables.add(poder)
    }

    fun eliminarPoderFavorable(poder : Poder) {
        this.poderesFavorables.remove(poder)
    }

    fun agregarPoderesFavorables(poderes: MutableSet<Poder>) {
        this.poderesFavorables.addAll(poderes)
    }

    fun cambiarCreadoPor(villano : Villano) {
        this.creadoPor = villano
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Conflicto

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (progresoHaciaSuResolucion != other.progresoHaciaSuResolucion) return false
        if (resueltoPor != other.resueltoPor) return false

        return true
    }
}