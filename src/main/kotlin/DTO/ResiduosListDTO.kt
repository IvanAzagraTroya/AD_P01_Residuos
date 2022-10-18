package DTO

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    //no usado para esta practica de momento
    fun fromJSON(json: String): ResiduosListDTO? {
        return Gson().fromJson(json, ResiduosListDTO::class.java)
    }

    //no usado para esta practica de momento
    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}