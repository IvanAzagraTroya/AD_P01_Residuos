package model

import javax.xml.bind.annotation.XmlAttribute

class Residuos() {
    constructor(
        lote: Int,
        año: String,
        mes: String,
        tipoResiduo: TipoResiduo,
        distrito: Int,
        nombreDistrito: String,
        toneladas: Double
    ): this() {
        this.lote = lote
        this.año = año
        this.mes = mes
        this.tipoResiduo = tipoResiduo
        this.distrito = distrito
        this.nombreDistrito = nombreDistrito
        this.toneladas = toneladas
    }

    @XmlAttribute(name = "lote")
    var lote: Int = 0
    @XmlAttribute(name = "year")
    var año: String = ""
    @XmlAttribute(name = "month")
    var mes: String = ""
    var tipoResiduo: TipoResiduo = TipoResiduo.RESTO
    var distrito: Int = 0
    var nombreDistrito: String = ""
    var toneladas: Double = 0.0
}