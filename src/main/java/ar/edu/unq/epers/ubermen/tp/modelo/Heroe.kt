package ar.edu.unq.epers.ubermen.tp.modelo

import javax.persistence.*

@Entity
class Heroe() : Personaje() {

    @OneToMany(mappedBy = "resueltoPor", fetch = FetchType.EAGER)
    var conflictosResueltos: MutableSet<Conflicto> = HashSet()

    constructor(nombre: String, vida: Int, imagenURL: String, conflictos: MutableSet<Conflicto>, poderes: MutableSet<Poder>) : this() {
        this.nombre = nombre
        this.vida = vida
        this.imagenURL = imagenURL
        this.conflictosResueltos = conflictos
        this.poderes = poderes
    }

    constructor(nombre: String, poderes: MutableSet<Poder>) : this() {
        this.nombre = nombre
        this.poderes = poderes
    }

    fun agregarConflicto(nuevoConflicto: Conflicto) {
        this.conflictosResueltos.add(nuevoConflicto)
    }

    fun eliminarConflicto(conflicto: Conflicto) {
        this.conflictosResueltos.remove(conflicto)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Heroe

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (vida != other.vida) return false
        if (imagenURL != other.imagenURL) return false

        return true
    }
}
