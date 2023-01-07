package ar.edu.unq.epers.ubermen.tp.service.impl.exception

class NoHayConflictosException : RuntimeException() {

    override val message: String?
        get() = "No hay conflictos cargados."

    companion object {

        private val serialVersionUID = 1L
    }
}