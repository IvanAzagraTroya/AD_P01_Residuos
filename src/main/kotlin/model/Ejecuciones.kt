package model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name="ejecuciones")
@XmlAccessorType(XmlAccessType.FIELD)
class Ejecuciones() {
    @XmlElementWrapper(name = "result_list")
    val resultList : MutableList<Ejecucion> = mutableListOf()
}