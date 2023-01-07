package ar.edu.unq.epers.ubermen.tp.service.impl.exception

class VidaErroneaException : RuntimeException() {

    override val message: String?
        get() = "Debe tener una vida correcta(de 0 a 100)."

    companion object {

        private val serialVersionUID = 1L
    }
}