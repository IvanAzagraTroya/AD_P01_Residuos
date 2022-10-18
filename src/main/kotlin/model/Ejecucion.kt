package model

import util.Util
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "execution_data")
@XmlAccessorType(XmlAccessType.FIELD)
class Ejecucion() {
    constructor(tipoOpcion: TipoOpcion, inicioEjecucion: Long, successfullExecution: Boolean) : this()
    {
        this.tipoOpcion = tipoOpcion
        this.inicioEjecucion = inicioEjecucion
        this.successfullExecution = successfullExecution
    }

    var tipoOpcion: TipoOpcion = TipoOpcion.PARSER
    var inicioEjecucion: Long = 0
    var successfullExecution: Boolean = false


    @XmlAttribute(name = "id")
    val id: UUID = UUID.randomUUID()
    @XmlAttribute(name = "execution_time")
    val executionTime = System.currentTimeMillis() - inicioEjecucion
    val instant: String = Util.getCurrentInstantForExecution()
    val selectedOption = when(tipoOpcion) {
        TipoOpcion.PARSER -> "parser"
        TipoOpcion.RESUMEN_CIUDAD -> "resumen ciudad"
        TipoOpcion.RESUMEN_GLOBAL -> "resumen global"
    }
    val wasSuccessful = successfullExecution
}
