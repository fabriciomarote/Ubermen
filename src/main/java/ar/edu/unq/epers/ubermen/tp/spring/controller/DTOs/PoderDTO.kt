package ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs

import ar.edu.unq.epers.ubermen.tp.modelo.Atributo
import ar.edu.unq.epers.ubermen.tp.modelo.Heroe
import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.modelo.exception.AtributoVacioException
import ar.edu.unq.epers.ubermen.tp.service.impl.exception.AtributoInvalidoException

class PoderDTO (
    var id: Long?,
    var nombre:String?,
    var cantidadPotenciada:Int,
    var atributo: String?) {

    companion object {
        fun desdeModelo(poder: Poder) =
            PoderDTO(
                id = poder.id,
                nombre = poder.nombre,
                cantidadPotenciada = poder.cantidadPotenciada,
                atributo = poder.atributoPotenciado.toString()
            )
    }

    fun aModelo(): Poder {
        val poder = Poder()
        poder.id = this.id
        poder.cambiarNombre(this.nombre!!)
        poder.cambiarCantidadPotenciada(this.cantidadPotenciada)
        poder.cambiarAtributoPotenciado(verificarAtributo(this.atributo!!))

        return poder
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