package model

import util.Util
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

@XmlRootElement(name = "execution_data")
@XmlAccessorType(XmlAccessType.FIELD)
class Ejecucion() {
    @XmlTransient
    var inicioEjecucion: Long = 0

    constructor(selectedOption: String, inicioEjecucion: Long, successfullExecution: Boolean) : this()
    {
        this.selectedOption = selectedOption
        this.inicioEjecucion = inicioEjecucion
        this.wasSuccessful = successfullExecution
    }

    @XmlAttribute(name = "id")
    val id: UUID = UUID.randomUUID()
    @XmlAttribute(name = "execution_time")
    val executionTime = System.currentTimeMillis() - inicioEjecucion
    val instant: String = Util.getCurrentInstantForExecution()
    var selectedOption: String = ""
    var wasSuccessful = false
}
