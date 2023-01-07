package ar.edu.unq.epers.ubermen.tp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
open class UberMenApp

fun main(args: Array<String>) {
    runApplication<UberMenApp>(*args)
}