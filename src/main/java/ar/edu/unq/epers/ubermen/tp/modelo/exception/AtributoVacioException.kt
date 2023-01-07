package ar.edu.unq.epers.ubermen.tp.modelo.exception

class AtributoVacioException : RuntimeException() {

    override val message: String?
        get() = "El atributo no puede ser vacío."

    companion object {

        private val serialVersionUID = 1L
    }
}