package ar.edu.unq.epers.ubermen.tp.spring.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
@CrossOrigin
class HomeController() {
    @GetMapping("")
    @ResponseBody
    fun index(): String {
        return "Hola"
    }
}