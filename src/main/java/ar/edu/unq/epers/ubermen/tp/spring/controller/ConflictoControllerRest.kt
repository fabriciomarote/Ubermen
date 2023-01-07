package ar.edu.unq.epers.ubermen.tp.spring.controller

import ar.edu.unq.epers.ubermen.tp.service.ConflictoService
import ar.edu.unq.epers.ubermen.tp.service.HeroeService
import ar.edu.unq.epers.ubermen.tp.service.impl.HeroeServiceImp
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.ConflictoDTO
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.HeroeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/conflicto")
class ConflictoControllerRest(@Autowired
                              private val conflictoService: ConflictoService,
                              @Autowired
                              private val heroeService : HeroeServiceImp) {

    @PostMapping("/")
    fun crear(@RequestBody conflicto: ConflictoDTO) = conflictoService.crear(conflicto.aModelo(heroeService))

    @PutMapping("/{Id}")
    fun actualizar(@PathVariable conflictoId: Long) = conflictoService.actualizar(conflictoService.recuperar(conflictoId.toInt())!!)

    @GetMapping("/{Id}")
    fun recuperar(@PathVariable conflictoId: Long) = ConflictoDTO.desdeModelo(conflictoService.recuperar(conflictoId.toInt())!!)

    @DeleteMapping("/{Id}")
    fun eliminar(@PathVariable conflictoId: Long) = conflictoService.eliminar(conflictoId.toInt())!!

    @GetMapping("/conflicto")
    fun recuperarTodos() = conflictoService.recuperarTodos().map { conflicto -> ConflictoDTO.desdeModelo(conflicto)  }
}