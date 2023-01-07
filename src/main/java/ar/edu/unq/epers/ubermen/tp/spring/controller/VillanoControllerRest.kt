package ar.edu.unq.epers.ubermen.tp.spring.controller

import ar.edu.unq.epers.ubermen.tp.service.VillanoService
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.VillanoDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/villano")
class VillanoControllerRest(@Autowired private val villanoService: VillanoService) {

    @PostMapping("/")
    fun crear(@RequestBody villano: VillanoDTO) = villanoService.crear(villano.aModelo())

    @PutMapping("/{Id}")
    fun actualizar(@PathVariable villanoId: Long) = villanoService.actualizar(villanoService.recuperar(villanoId.toInt()))

    @GetMapping("/{Id}")
    fun recuperar(@PathVariable villanoId: Long) = VillanoDTO.desdeModelo(villanoService.recuperar(villanoId.toInt())!!)

    @GetMapping("")
    fun recuperarTodos() = villanoService.recuperarTodos().map { villano -> VillanoDTO.desdeModelo(villano)  }

    @DeleteMapping("/{Id}")
    fun eliminar(@PathVariable villanoId: Long) = villanoService.eliminar(villanoId.toInt())!!
}