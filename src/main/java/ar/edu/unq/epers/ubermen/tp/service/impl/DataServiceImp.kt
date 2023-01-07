package ar.edu.unq.epers.ubermen.tp.service.impl

import ar.edu.unq.epers.ubermen.tp.service.*
import org.springframework.beans.factory.annotation.Autowired

class DataServiceImp(
    @Autowired
    private val poderService: PoderService,
    private val heroeService: HeroeService,
    private val conflictoService: ConflictoService,
    private val pruebaDeHabilidadService: PruebaDeHabilidadService,
    private val villanoService: VillanoService,
) : DataService {

    override fun cleanup() {
        poderService.clear()
        heroeService.clear()
        conflictoService.clear()
        pruebaDeHabilidadService.clear()
        villanoService.clear()
    }
}