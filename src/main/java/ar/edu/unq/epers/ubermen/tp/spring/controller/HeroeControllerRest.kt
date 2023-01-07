package ar.edu.unq.epers.ubermen.tp.spring.controller

import ar.edu.unq.epers.ubermen.tp.service.HeroeService
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.HeroeDTO
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/heroe")
class HeroeControllerRest(private val heroeService: HeroeService) {

    @PostMapping("/heroe")
    fun crear(@RequestBody heroe: HeroeDTO) = heroeService.crear(heroe.aModelo())

    @PutMapping("/{Id}")
    fun actualizar(@PathVariable heroeId: Long) = heroeService.actualizar(heroeService.recuperar(heroeId.toInt())!!)

    @GetMapping("/{Id}")
    fun recuperar(@PathVariable heroeId: Long) = HeroeDTO.desdeModelo(heroeService.recuperar(heroeId.toInt())!!)

    @GetMapping("/heroe")
    fun recuperarTodos() = heroeService.recuperarTodos().map { heroe -> HeroeDTO.desdeModelo(heroe)  }

    @DeleteMapping("/{Id}")
    fun eliminar(@PathVariable heroeId: Long) = heroeService.eliminar(heroeId.toInt())!!

}