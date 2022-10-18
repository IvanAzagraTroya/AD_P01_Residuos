package DTO

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    //no usado para esta practica de momento
    fun fromJSON(json: String): ContenedoresListDTO? {
        return Gson().fromJson(json, ContenedoresListDTO::class.java)
    }

    //no usado para esta practica de momento
    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}