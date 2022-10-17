package log

import model.Ejecucion
import model.Ejecuciones
import java.io.File
import java.lang.Boolean
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import kotlin.Throws

object BitacoraCreator {
    private val PATH_TO_BITACORA_XML = "${System.getProperty("user.dir")}${File.separator}bitacora${File.separator}bitacora.xml"
    private val results = Ejecuciones()

    @Throws(JAXBException::class)
    fun saveIntoBitacora(execution: Ejecucion) {
        val bitacoraDirectoryURI = "${System.getProperty("user.dir")}${File.separator}bitacora"
        val bitacoraDirectory = File(bitacoraDirectoryURI)
        if (!bitacoraDirectory.exists()) {
            bitacoraDirectory.mkdirs()
        }
        val file = File(PATH_TO_BITACORA_XML)
        if (!file.exists()) {
            createCosas(execution)
        } else {
            val jaxbContext: JAXBContext = JAXBContext.newInstance(Ejecuciones::class.java)
            val unmarshaller = jaxbContext.createUnmarshaller()
            val executions: Ejecuciones = unmarshaller.unmarshal(File(PATH_TO_BITACORA_XML)) as Ejecuciones
            var duplicate = false
            for (res in executions.resultList) {
                if (res.instant.uppercase() == execution.instant.uppercase() &&
                    res.execution.name.uppercase() == execution.execution.name.uppercase()
                ) {
                    duplicate = true
                }
            }
            if (!duplicate) {
                executions.resultList.add(execution)
            }
            resultsToXML(executions)
        }
    }

    @Throws(JAXBException::class)
    private fun resultsToXML(executions: Ejecuciones) {
        val jaxbContext: JAXBContext = JAXBContext.newInstance(Ejecuciones::class.java)
        val marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE)
        marshaller.marshal(results, File(PATH_TO_BITACORA_XML))
    }

    @Throws(JAXBException::class)
    private fun createCosas(execution: Ejecucion) {
        val ejecuciones = Ejecuciones()
        ejecuciones.resultList.add(execution)
        resultsToXML(ejecuciones)
    }
}