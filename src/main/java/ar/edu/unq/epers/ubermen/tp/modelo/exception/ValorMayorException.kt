package ar.edu.unq.epers.ubermen.tp.modelo.exception

class ValorMayorException : RuntimeException()  {

    override val message: String?
        get() = "El valor no puede ser mayor a 10."

    companion object {

        private val serialVersionUID = 1L
    }
}