package model

import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Iván Azagra y Daniel Rodriguez
 * Clase KOJO de los residuos
 */
@XmlRootElement(name = "residuos_data")
@XmlAccessorType(XmlAccessType.FIELD)
@DataSchema
class Residuos() {
    constructor(
        lote: Int,
        year: String,
        mes: String,
        tipoResiduo: TipoResiduo,
        distrito: Int,
        nombreDistrito: String,
        toneladas: Double
    ): this() {
        this.lote = lote
        this.year = year
        this.mes = mes
        this.tipoResiduo = tipoResiduo
        this.distrito = distrito
        this.nombreDistrito = nombreDistrito
        this.toneladas = toneladas
    }

    @XmlAttribute(name = "lote")
    var lote: Int = 0
    @XmlAttribute(name = "year")
    var year: String = ""
    @XmlAttribute(name = "month")
    var mes: String = ""
    var tipoResiduo: TipoResiduo = TipoResiduo.RESTO
    var distrito: Int = 0
    var nombreDistrito: String = ""
    var toneladas: Double = 0.0
}