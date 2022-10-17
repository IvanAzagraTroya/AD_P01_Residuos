package dataWorker

import java.awt.Desktop
import java.io.File

fun OpenInBrowser(content: String, fileName: String) {
    val dir = File(System.getProperty("user.dir"), "resources"+File.separator+"images")
    dir.mkdir()

    val file = File(dir.canonicalPath, fileName)
    file.createNewFile()
    file.writeText(content)

    try {
        Desktop.getDesktop().browse(file.toURI())
    } catch (e: Exception){
        println("Error al abrir en el navegador: ${e.message}")
    }
}

//fun Plot.exportToHtml() =
//    PlotHtmlExport.buildHtmlFromRawSpecs(this.toSpec(), scriptUrl(VersionChecker.letsPlotJsVersion))
//
//fun Plot.exportToSvg() = PlotSvgExport.buildSvgImageFromRawSpecs(this.toSpec())