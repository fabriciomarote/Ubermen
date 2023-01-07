package ar.edu.unq.epers.ubermen.tp.spring.configuration

import ar.edu.unq.epers.ubermen.tp.persistence.PoderDAO
import ar.edu.unq.epers.ubermen.tp.persistence.PoderMutacionDAO
import ar.edu.unq.epers.ubermen.tp.service.PoderService
import ar.edu.unq.epers.ubermen.tp.service.impl.PoderServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfiguration {

    @Autowired
    lateinit var poderDAO: PoderDAO

    @Autowired
    lateinit var poderMutacionDAO: PoderMutacionDAO

    @Bean
    open fun poderService(): PoderService {
        return PoderServiceImp(poderDAO,poderMutacionDAO)
    }
}