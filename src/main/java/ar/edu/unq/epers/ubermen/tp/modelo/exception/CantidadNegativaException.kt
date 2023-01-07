package ar.edu.unq.epers.ubermen.tp.modelo.exception

class CantidadNegativaException : RuntimeException() {

    override val message: String?
        get() = "La cantidad no puede ser negativa."

    companion object {

        private val serialVersionUID = 1L
    }
}