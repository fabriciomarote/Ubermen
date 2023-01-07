package ar.edu.unq.epers.ubermen.tp.spring.controller

import ar.edu.unq.epers.ubermen.tp.service.PruebaDeHabilidadService
import ar.edu.unq.epers.ubermen.tp.spring.controller.DTOs.PruebaDeHabilidadDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/prueba")
class PruebaDeHabilidadControllerRest(@Autowired private val pruebaService: PruebaDeHabilidadService) {
    @PostMapping("/")
    fun crear(@RequestBody pruebaDeHabilidad: PruebaDeHabilidadDTO) = pruebaService.crear(pruebaDeHabilidad.aModelo())

    @PutMapping("/{Id}")
    fun actualizar(@PathVariable pruebaId: Long) = pruebaService.actualizar(pruebaService.recuperar(pruebaId.toInt()))

    @GetMapping("/{Id}")
    fun recuperar(@PathVariable pruebaId: Long) = PruebaDeHabilidadDTO.desdeModelo(pruebaService.recuperar(pruebaId.toInt())!!)

    @GetMapping("")
    fun recuperarTodos() = pruebaService.recuperarTodos().map { prueba -> PruebaDeHabilidadDTO.desdeModelo(prueba)  }

    @DeleteMapping("/{Id}")
    fun eliminar(@PathVariable pruebaId: Long) = pruebaService.eliminar(pruebaId.toInt())!!
}