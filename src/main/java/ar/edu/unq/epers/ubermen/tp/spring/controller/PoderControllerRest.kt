package ar.edu.unq.epers.ubermen.tp.spring.controller

import ar.edu.unq.epers.ubermen.tp.modelo.Poder
import ar.edu.unq.epers.ubermen.tp.service.PoderService
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.ConflictoDTO
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.PoderDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/poder")
class PoderControllerRest(@Autowired
                          private val poderService: PoderService
) {

    @PostMapping
    fun crear(@RequestBody poderDTO: PoderDTO) = poderService.crear(poderDTO.aModelo())

    @PutMapping("/{Id}")
    fun actualizar(@PathVariable poderId: Long) = poderService.actualizar(poderService.recuperar(poderId.toInt()))

    @GetMapping("/{Id}")
    fun recuperar(@PathVariable poderId: Long) = PoderDTO.desdeModelo(poderService.recuperar(poderId.toInt())!!)

    @GetMapping("")
    fun recuperarTodos() = poderService.recuperarTodos().map { poder -> PoderDTO.desdeModelo(poder)  }

    @DeleteMapping("/{Id}")
    fun eliminar(@PathVariable poderId: Long) = poderService.eliminar(poderId.toInt())!!
}