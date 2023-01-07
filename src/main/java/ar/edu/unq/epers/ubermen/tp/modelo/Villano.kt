package ar.edu.unq.epers.ubermen.tp.modelo

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class Villano() : Personaje() {

    @OneToMany(mappedBy = "creadoPor", fetch = FetchType.EAGER)
    var conflictosComenzados: MutableSet<Conflicto> = HashSet()

    constructor(nombre: String, vida: Int, imagenURL: String, conflictos: MutableSet<Conflicto>, poderes: MutableSet<Poder>) : this() {
        this.nombre = nombre
        this.vida = vida
        this.imagenURL = imagenURL
        this.conflictosComenzados = conflictos
        this.poderes = poderes
    }

    fun agregarConflicto(nuevoConflicto: Conflicto) {
        this.conflictosComenzados.add(nuevoConflicto)
    }

    fun eliminarConflicto(conflicto: Conflicto) {
        this.conflictosComenzados.remove(conflicto)
    }

    fun creacionConflicto(nombreConflicto: String) : Conflicto {
        val conflicto = Conflicto(nombreConflicto)
        conflicto.agregarPoderesFavorables(this.poderes)
        conflicto.cambiarCreadoPor(this)
        this.agregarConflicto(conflicto)
        return conflicto
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Villano

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (vida != other.vida) return false
        if (imagenURL != other.imagenURL) return false

        return true
    }
}