package web

import lectores.CSVReader
import model.TipoContenedor
import java.io.File

object DataCalculator {
    var mediaContenedores: String = "";
    val csvContenedores: String = "data${File.separator}contenedores_varios.csv"
    val content = CSVReader.readCSVContenedores(csvContenedores, ";")

    fun calculateMediaContenedores(): Int {
        var org = 0
        var resto = 0
        var envases = 0
        var vidrio = 0
        var pap = 0
        var data = content.forEach{
            val distrito = it.distrito
            val type = it.tipoContenedor

            when(type) {
                TipoContenedor.ORGANICA -> org++
                TipoContenedor.RESTO -> resto++
                TipoContenedor.ENVASES -> envases++
                TipoContenedor.VIDRIO -> vidrio++
                TipoContenedor.PAPEL_Y_CARTON -> pap++
                else -> 0
            }
        }

        // Cómo se saca una media por tipo? Si la media se saca de un conjunto de datos agrupado dividiéndolos por el número común,
        // es decir en este caso hay 5 tipos de contenedores, entonces el número total de contenedores se debería dividir por ese mismo
        //entonces lo que se pide es simplemente el size/5 o se pide que en el de la comunidad de Madrid saquemos
        // la suma de todos los contenedores partido por el número de distritos
        var media = (org + resto + envases + vidrio + pap)/ número de distritos
        return media
    }

    fun calculaMediaContenedoresDistrito() {
        
    }
}