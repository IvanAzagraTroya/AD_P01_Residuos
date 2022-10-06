package web

import lectores.CSVReader
import model.TipoContenedor
import java.io.File

object DataCalculator {
    var mediaContenedores: String = "";
    val csvContenedores: String = "data${File.separator}contenedores_varios.csv"
    val content = CSVReader.readCSVContenedores(csvContenedores, ";")

    fun calculateMediaContenedores(): () -> Int {
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
        var media = {(org + resto + envases + vidrio + pap)/5}
        return media
    }
}