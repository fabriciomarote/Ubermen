package ar.edu.unq.epers.ubermen.tp.modelo.exception

class AtributoErrorValorException : RuntimeException() {

    override val message: String?
        get() = "El valor del atributo debe ser mayor a 0 y menor o igual a 100."

    companion object {

        private val serialVersionUID = 1L
    }
}