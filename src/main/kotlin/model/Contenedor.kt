package model

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Iván Azagra Troya y Daniel Rodriguez
 * Clase KOJO de Contenedores
 */
@Serializable
@XmlRootElement(name = "contenedor_data")
@XmlAccessorType(XmlAccessType.FIELD)
@DataSchema
class Contenedor() {
    constructor(
        codigoSituado: String = "",
        tipoContenedor: TipoContenedor,
        modelo: String = "",
        descripcionModelo: String = "",
        cantidad: Int,
        lote: Int,
        distrito: String = "",
        barrio: String = "",
        tipoVia: String = "",
        nombreCalle: String = "",
        numero: String = ""
    ) : this() {
        this.codigoSituado = codigoSituado
        this.tipoContenedor = tipoContenedor
        this.modelo = modelo
        this.descripcionModelo = descripcionModelo
        this.cantidad = cantidad
        this.lote = lote
        this.distrito = distrito
        this.barrio = barrio
        this.tipoVia = tipoVia
        this.nombreCalle = nombreCalle
        this.numero = numero
    }

    var codigoSituado: String = ""
    var tipoContenedor: TipoContenedor = TipoContenedor.RESTO
    var modelo: String = ""
    var descripcionModelo: String = ""
    var cantidad: Int = 0
    var lote: Int = 0
    var distrito: String = ""
    var barrio: String = ""
    var tipoVia: String = ""
    var nombreCalle: String = ""
    var numero: String = ""
}
