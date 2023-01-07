package ar.edu.unq.epers.ubermen.tp.service.impl.exception

class SinPoderException : RuntimeException() {

    override val message: String?
        get() = "Debe tener al menos un poder."

    companion object {

        private val serialVersionUID = 1L
    }
}