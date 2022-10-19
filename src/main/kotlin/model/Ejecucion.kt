package model

import util.Util
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Daniel Rodriguez Muñoz
 * Clase POKO para el objeto de la ejecución
 */
@XmlRootElement(name = "execution_data")
@XmlAccessorType(XmlAccessType.FIELD)
class Ejecucion() {
    constructor(selectedOption: String, inicioEjecucion: Long, successfullExecution: Boolean) : this()
    {
        this.selectedOption = selectedOption
        this.executionTime = System.currentTimeMillis() - inicioEjecucion
        this.wasSuccessful = successfullExecution
    }

    @XmlAttribute(name = "id")
    val id: UUID = UUID.randomUUID()
    @XmlAttribute(name = "execution_time")
    var executionTime = 0L
    val instant: String = Util.getCurrentInstantForExecution()
    var selectedOption: String = ""
    var wasSuccessful = false
}
