package DTO

import model.Residuos
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name="residuos")
@XmlAccessorType(XmlAccessType.FIELD)
class ResiduosListDTO() {
    constructor(list: List<Residuos>) : this() {
        this.resultList = list
    }
    @XmlElementWrapper(name = "residuos_list")
    var resultList : List<Residuos> = listOf()
}