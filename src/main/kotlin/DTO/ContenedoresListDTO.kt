package DTO

import model.Contenedor
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name="contenedores")
@XmlAccessorType(XmlAccessType.FIELD)
class ContenedoresListDTO() {
    constructor(list: List<Contenedor>) : this() {
        this.resultList = list
    }
    @XmlElementWrapper(name = "contenedor_list")
    var resultList : List<Contenedor> = listOf()
}