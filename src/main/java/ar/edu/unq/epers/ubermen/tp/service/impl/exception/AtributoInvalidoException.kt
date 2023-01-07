package ar.edu.unq.epers.ubermen.tp.service.impl.exception

class AtributoInvalidoException: RuntimeException() {

    override val message: String?
        get() = "El atributo ingresado es invalido"

    companion object {

        private val serialVersionUID = 1L
    }
}