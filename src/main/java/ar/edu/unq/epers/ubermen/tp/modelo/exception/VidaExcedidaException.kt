package ar.edu.unq.epers.ubermen.tp.modelo.exception

class VidaExcedidaException : RuntimeException() {

    override val message: String?
        get() = "La vida no puede ser mayor a 100."

    companion object {

        private val serialVersionUID = 1L
    }
}