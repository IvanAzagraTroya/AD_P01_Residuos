package model

import DTO.ExecutionDTO
import util.Util
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "execution_data")
@XmlAccessorType(XmlAccessType.FIELD)
class Ejecucion(execDto: ExecutionDTO, tipoOpcion: TipoOpcion, inicioEjecucion: Long, successfullExecution: Boolean) {
    @XmlAttribute(name = "id")
    val id: UUID = UUID.randomUUID()
    val instant: String = Util.getCurrentInstantForExecution()
    val tiempoEjecucionMillis = System.currentTimeMillis() - inicioEjecucion
    val selectedOption = when(tipoOpcion) {
        TipoOpcion.PARSER -> "parser"
        TipoOpcion.RESUMEN_CIUDAD -> "resumen ciudad"
        TipoOpcion.RESUMEN_GLOBAL -> "resumen global"
    }
    val execution = execDto
    val wasSuccessful = successfullExecution
}
