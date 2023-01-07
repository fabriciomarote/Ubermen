package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.PruebaDeHabilidad
import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException

class PruebaDeHabilidadDTO (
    var id:Long?= null,
    var nombre:String?= null,
    var dificultad: Int= 0,
    var cantidadDeExitos: Int= 0,
    var retalacion: Int= 0,
    var atributoDesafiado: String? = null
) {

    companion object {
        fun desdeModelo(prueba: PruebaDeHabilidad) =
            PruebaDeHabilidadDTO(
                id = prueba.id,
                nombre = prueba.nombre,
                dificultad = prueba.dificultad,
                cantidadDeExitos = prueba.cantidadDeExitos,
                retalacion = prueba.retalacion,
                atributoDesafiado = prueba.atributoDesafiado.toString()
            )
    }

    fun aModelo(): PruebaDeHabilidad {
        val prueba = PruebaDeHabilidad()
        prueba.id = this.id
        prueba.cambiarNombre(this.nombre!!)
        prueba.cambiarDificultad(this.dificultad)
        prueba.cambiarCantidadDeExitos(this.cantidadDeExitos)
        prueba.cambiarRetalacion(this.retalacion)
        prueba.cambiarAtributoDesafiado(this.verificarAtributo(this.atributoDesafiado!!))
        return prueba
    }

    private fun verificarAtributo(atributo: String?): Atributo {
        var nuevoAtributo: Atributo
        when(atributo) {
            "Fuerza" -> nuevoAtributo = Atributo.FUERZA
            "Destreza" -> nuevoAtributo = Atributo.DESTREZA
            "Constitucion" -> nuevoAtributo = Atributo.CONSTITUCION
            "Inteligencia" -> nuevoAtributo = Atributo.INTELIGENCIA
            else -> {
                throw AtributoVacioException()
            }
        }
        return nuevoAtributo
    }
}