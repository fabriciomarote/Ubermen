package ar.edu.unq.epers.ubermen.tp.modelo

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class HeroeNeo() {
    @Id
    var id: Long? = null

    var nombre : String? = null

    @Relationship(type = "CONTADOR")
    var contadores : List<Contador> = emptyList()

    @Relationship(type = "PODER")
    var poderes : List<PoderNeo> = emptyList()

    constructor(id: Long, nombre: String) : this() {
        this.id = id
        this.nombre = nombre
    }

    fun fromHeroe(heroe : Heroe) : HeroeNeo {
        this.id = heroe.id
        this.nombre = heroe.nombre
        heroe.poderes.map { poder ->
            var poderNeo = PoderNeo().fromPoder(poder)
            this.poderes += poderNeo
        }
        return this
    }

    private fun estaEnContador(condicion : Condicion) : Boolean {
        return this.buscarContador(condicion).condicion != null
    }

    private fun buscarContador(condicion : Condicion) : Contador {
        var contadorBuscado = contadores.find { contador -> contador.condicion == condicion }
        if(contadorBuscado == null) {
            contadorBuscado = Contador()
        }
        return contadorBuscado
    }

    fun agregarCondicion(condicion : Condicion) {
        if(this.estaEnContador(condicion)) {
            throw RuntimeException("La condición ya se encuentra en el heroe.")
        }
        this.contadores += Contador(condicion)
    }

    fun eliminarCondicion(condicion : Condicion) {
        if(!this.estaEnContador(condicion)) {
            throw RuntimeException("La condición no se encuentra en el heroe.")
        }
        var contador = this.buscarContador(condicion)
        this.contadores -= contador
    }

    fun incrementarContador(condicion : Condicion) {
        if(!this.estaEnContador(condicion)) {
            throw RuntimeException("La condición no se encuentra en el heroe.")
        }
        var contadorBuscado = this.buscarContador(condicion)
        contadores.map { contador ->
            if(contadorBuscado == contador) {
                contador.incrementarContador()
            }
        }
    }

    fun terminoCondicion(condicion : Condicion) : Boolean {
        if(!this.estaEnContador(condicion)) {
            throw RuntimeException("La condición no se encuentra en el heroe.")
        }
        var contador = this.buscarContador(condicion)
        return contador.terminoCondicion()
    }

    fun agregarPoder(poderNeo: PoderNeo) {
        this.validarPoder(poderNeo)
        poderes += poderNeo
        this.mappearCondiciones(poderNeo)
    }

    private fun mappearCondiciones(poderNeo: PoderNeo) {
        poderNeo.condiciones.map {
                condicion -> this.agregarCondicion(condicion)
        }
    }

    private fun validarPoder(poderNeo: PoderNeo) {
        if(poderes.contains(poderNeo)) {
            throw RuntimeException("El heroeNeo ya tiene este poderNeo.")
        }
    }
}