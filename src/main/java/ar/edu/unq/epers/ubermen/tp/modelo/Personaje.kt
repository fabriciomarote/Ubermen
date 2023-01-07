package ar.edu.unq.epers.ubermen.tp.modelo

import ar.edu.unq.epers.ubermen.tp.modelo.exception.CantidadNegativaException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.StringVacioException
import ar.edu.unq.epers.ubermen.tp.modelo.exception.VidaExcedidaException
import javax.persistence.*

@Entity
class Personaje() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre : String? = null
    var vida: Int = 0
    var imagenURL: String? = null
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="personaje_poderes",
        joinColumns= [JoinColumn(name="personaje_id", referencedColumnName="id")],
        inverseJoinColumns= [JoinColumn(name="poder_id", referencedColumnName="id")]
    )
    var poderes: MutableSet<Poder> = HashSet()

    constructor(nombre: String, vida: Int, poderes: MutableSet<Poder>) : this() {
        this.nombre = nombre
        this.vida = vida
        this.poderes = poderes
    }

    fun cambiarNombre(nombreNuevo: String) {
        if(nombreNuevo == "") {
            throw StringVacioException()
        }
        this.nombre = nombreNuevo
    }

    fun cambiarImagen(nuevaImagen: String) {
        if(nuevaImagen == "") {
            throw StringVacioException()
        }
        this.imagenURL = nuevaImagen
    }

    fun cambiarVida(nuevaVida: Int) {
        if(nuevaVida < 0) {
            throw CantidadNegativaException()
        } else if (nuevaVida > 100) {
            throw VidaExcedidaException()
        }
        this.vida = nuevaVida
    }

    fun restarVida(cant: Int) {
        if ((this.vida - cant) < 0) {
            this.vida = 0
        } else {
            this.vida -= cant
        }
    }

    fun sumarVida(cant: Int) {
        if ((this.vida + cant) > 100) {
            this.vida = 100
        }
        else {
            this.vida += cant
        }
    }

    fun poseerPoder(nuevoPoder: Poder) {
        this.poderes.add(nuevoPoder)
    }

    fun olvidarPoder(poder: Poder) {
        this.poderes.remove(poder)
    }

    fun estaMuerto() : Boolean {
        return vida <= 0
    }

    fun getConstitucion() : Int {
        val constitucion = 1
        val resultado = constitucion + this.mapearPoderes(Atributo.CONSTITUCION)
        return this.validarResultado(resultado)
    }

    fun getFuerza() : Int {
        val fuerza = 1
        val resultado = fuerza + this.mapearPoderes(Atributo.FUERZA)
        return this.validarResultado(resultado)
    }

    fun getInteligencia() : Int {
        val inteligencia = 1
        val resultado = inteligencia + this.mapearPoderes(Atributo.INTELIGENCIA)
        return this.validarResultado(resultado)
    }

    fun getDestreza() : Int {
        val destreza = 1
        val resultado = destreza + this.mapearPoderes(Atributo.DESTREZA)
        return this.validarResultado(resultado)
    }

    private fun mapearPoderes(atributo : Atributo) : Int {
        var cant = 0
        poderes.forEach { poder ->
            if(poder.atributoPotenciado == atributo) {
                cant += poder.cantidadPotenciada
            }
        }
        return cant
    }

    private fun validarResultado(cant : Int) : Int {
        var resultado = cant
        if(resultado > 100) {
            resultado = 100
        } else if(resultado < 1){
            resultado = 1
        }
        return resultado
    }
}