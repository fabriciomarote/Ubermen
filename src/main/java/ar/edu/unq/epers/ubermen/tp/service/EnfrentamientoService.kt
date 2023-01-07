
package ar.edu.unq.epers.ubermen.tp.service

import ar.edu.unq.epers.ubermen.tp.modelo.Conflicto

interface EnfrentamientoService {
    fun obtenerConflictoAleatorio(idHeroe: Long): Conflicto
    fun enfrentar(heroeId:Int, conflictoId:Int): Conflicto
    fun clear()
}