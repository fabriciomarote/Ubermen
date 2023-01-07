package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.neo4j.core.schema.*

@Node
class Condicion() {
    @Id
    @GeneratedValue
    var id: Long? = null

    var cantidad : Int? = null
    var evaluacion : Evaluacion? = null
    var atributo : Atributo? = null
    var resultado : Resultado? = null

    @Relationship(type = "TARGET_NODE")
    var poderNeo : PoderNeo? = null

    constructor(cantidad: Int, evaluacion: Evaluacion, atributo: Atributo, resultado: Resultado) : this() {
        this.cambiarCantidad(cantidad)
        this.cambiarEvaluacion(evaluacion)
        this.cambiarAtributo(atributo)
        this.cambiarResultado(resultado)
    }

    fun cambiarTargetNode(poderNeo_: PoderNeo) {
        poderNeo = poderNeo_
    }

    fun cambiarCantidad(cant : Int) {
        this.validarCantidad(cant)
        this.cantidad = cant
    }

    fun cambiarEvaluacion(evaluacion : Evaluacion) {
        this.validarEvaluacion(evaluacion)
        this.evaluacion = evaluacion
    }

    fun cambiarAtributo(atributo : Atributo) {
        this.validarAtributo(atributo)
        this.atributo = atributo
    }

    fun cambiarResultado(resultado : Resultado) {
        this.validarResultado(resultado)
        this.resultado = resultado
    }

    private fun validarCantidad(cant : Int) {
        if(cant <= 0) {
            throw RuntimeException("La cantidad debe ser mayor a 0.")
        }
    }

    private fun validarEvaluacion(evaluacion : Evaluacion) {
        if(evaluacion != Evaluacion.PRUEBASDEHABILIDAD && evaluacion != Evaluacion.EXITOSDETIRADAS) {
            throw RuntimeException("La evaluacion debe ser pruebas de habilidad o exitos de tiradas.")
        }
    }

    private fun validarAtributo(atributo : Atributo) {
        if(atributo != Atributo.INTELIGENCIA && atributo != Atributo.DESTREZA
            && atributo != Atributo.FUERZA && atributo != Atributo.CONSTITUCION) {
            throw RuntimeException("El atributo debe ser Inteligencia, Destreza, Fuerza o Constitucion.")
        }
    }

    private fun validarResultado(resultado : Resultado) {
        if(resultado != Resultado.SUPERADAS && resultado != Resultado.FALLADAS && resultado != Resultado.ACUMULADOS) {
            throw RuntimeException("El resultado debe ser superadas, falladas o acumulados.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Condicion

        if (id != other.id) return false
        if (cantidad != other.cantidad) return false
        if (evaluacion != other.evaluacion) return false
        if (atributo != other.atributo) return false
        if (resultado != other.resultado) return false
        if (poderNeo != other.poderNeo) return false

        return true
    }
}