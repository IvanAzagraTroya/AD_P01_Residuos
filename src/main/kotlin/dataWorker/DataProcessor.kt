package dataWorker

import jetbrains.datalore.base.values.Color
import jetbrains.letsPlot.Stat.identity
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import model.Contenedor
import model.Residuos
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.html
import util.Util
import java.awt.Desktop
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.math.roundToInt
import kotlin.system.exitProcess

/**
 * @author Iván Azagra Troya
 * Clase encargada de procesar los datos de los csv con Dataframes
 * @param contenedorData lista de contenedores obtenida desde el lector del csv
 * @param residuoData lista de residuos obtenida desde el lector del csv
 */

class DataProcessor(val contenedorData: List<Contenedor>, val residuoData: List<Residuos>, val initialExecutionTimeMillis: Long, val distrito: String?, destinationDirectory: String) {
    private val dir: String = System.getProperty("user.dir")
    private val imagesPath = File(dir+File.separator+"graphics"+File.separator)
    private val contDataframe = contenedorData.toDataFrame()
    private val residuoDataframe = residuoData.toDataFrame()

    lateinit var contenedoresDistrito: String
    lateinit var mediaToneladasAnuales: String
    lateinit var MaxMinMeanStd: String
    lateinit var sumaRecogidoDistrito: String
    lateinit var cantidadTipoRecogido: String

    /*variable de dirección del css de la web*/
    var css: String = "${System.getProperty("user.dir")}${File.separator}graphics${File.separator}style.css"

    init{
        dataToDataFrame()
        if (distrito == null) {
            graphics()
        } else {
            graphicsDistrito()
        }

        if (distrito == null) {
            generateHTML(generateSummary(),destinationDirectory,false)
        } else {
            generateHTML(generateDistrictSummary(),destinationDirectory,true)
        }
    }

    fun dataToDataFrame() {
        if(!imagesPath.exists())
            imagesPath.mkdir()

        contDataframe.schema().print()
        residuoDataframe.schema().print()

        // Tipo de contenedor por distrito
        contenedoresDistrito = contDataframe.groupBy("tipoContenedor", "distrito").count().html()

        // Jose Luis te he fallado no sé cómo hacer la media de contenedores por distrito

        mediaToneladasAnuales = residuoDataframe.groupBy("year", "nombreDistrito", "tipoResiduo")
            .aggregate {
                mean("toneladas").roundToInt() into "Media de toneladas anuales por distrito"
            }.html()

        MaxMinMeanStd = residuoDataframe.groupBy("nombreDistrito","toneladas", "tipoResiduo", "year")
            .aggregate {
                max("toneladas") into "Max toneladas"
                min("toneladas") into "Min toneladas"
                mean("toneladas").roundToInt() into "Media toneladas"
                std("toneladas") into "Desviacion toneladas"
            }.sortBy("nombreDistrito").html()

        sumaRecogidoDistrito = residuoDataframe.groupBy("nombreDistrito","toneladas", "year")
            .aggregate {
                sum("toneladas") into "Suma de toneladas recogidas"
            }.html()

        cantidadTipoRecogido = residuoDataframe.groupBy("nombreDistrito","tipoResiduo")
            .aggregate{
                sum("toneladas") into "Cantidad de toneladas"
            }.html()
    }

    fun graphics() {
        var d = contDataframe.groupBy("distrito", "tipoContenedor").count().toMap()
        val gTotalContenedores: Plot = letsPlot(data = d) + geomBar(
            stat = identity,
            alpha = 0.4,
            fill = Color.BLUE
        ) {
            x = "distrito"
            y = "count"
        } + labs(
            x = "distrito",
            y = "contendores por distrito",
            title = "Número de contenedores por distrito"
        )
        ggsave(gTotalContenedores, "Contenedores-Por-Distrito.png", 1, 2, imagesPath.toString())

        var d2 = residuoDataframe.groupBy("mes", "distrito").aggregate {
            mean("toneladas") into "media de toneladas mensuales"
        }.toMap()
        val gMediaToneladasMensuales: Plot = letsPlot(data = d2) +geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
        ) {
            x = "mes"
            y = "media de toneladas mensuales"
        } + labs(
            x = "mes",
            y = "toneladas",
            title = "Media de toneladas mensuales por distrito"
        )
        ggsave(gMediaToneladasMensuales, "Media-Toneladas-Mensuales-Distrito.png", 1, 2, imagesPath.toString())

    }

    fun graphicsDistrito() {
        var d = residuoDataframe.groupBy("nombreDistrito", "mes", "tipoResiduo")
            .aggregate {
                max("toneladas") into "Max toneladas"
                min("toneladas") into "Min toneladas"
                mean("toneladas") into "Media toneladas"
                std("toneladas") into "Desviacion toneladas" // Puede salir NaN por algún motivo
            }.toMap()

        val gMaxMinMeanStd:Plot = letsPlot(data = d) + geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN
        ) {
            x = "mes"
            y = "Max toneladas"
        } +geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.DARK_BLUE
        ){
            x = "mes"
            y = "Min toneladas"
        } +geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.DARK_GREEN
        ) {
            x = "mes"
            y = "Media toneladas"
        } +geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.GREEN
        ){
            x = "mes"
            y = "Desviacion toneladas"
        } + labs(
            x = "mes",
            y = "toneladas",
            title = "Maximo, minimo y media de residuos"
        )
        ggsave(gMaxMinMeanStd, "Max-Min-Media-Desviacion-Distrito.png", 1, 2, imagesPath.toString())

        var d2 = residuoDataframe.groupBy("nombreDistrito", "toneladas", "tipoResiduo")
            .aggregate {
                sum("toneladas") into "Total de toneladas por residuo"
            }.toMap()

        val gTotalToneladas:Plot = letsPlot(data = d2) + geomBar(
            stat = identity,
            alpha = 0.3,
            fill = Color.CYAN
        ) {
            x = "nombreDistrito"
            y = "Total de toneladas por residuo"
        }  +geomPoint(
            stat = identity,
            alpha = 0.6,
            fill = Color.DARK_BLUE
        ) {
            x = "nombreDistrito"
            y = "tipoResiduo"
        }+ labs(
            x = "distrito",
            y = "toneladas",
            title = "Toneladas totales por tipo de residuo"
        )
        ggsave(gTotalToneladas, "Toneladas-Totales-Tipo-Residuo.png", 1, 2, imagesPath.toString())
    }
    private fun generateSummary(): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="$css"/>
                
                <style type="text/css"/>
                
                <title>Práctica Acceso a Datos 01</title>
            </head>
            <body>
                <!--Archivo resumen Madrid"-->
                <h1 align="center" >Historial de recogida de basura y reciclaje de Madrid </h1>

                <br>
                <div id="contenedor">
                    <h4>Contenedores por tipo en cada distrito: </h4>
                    <p>$contenedoresDistrito</p>

                    <p>
                        <h4>Contenedores totales por distrito</h4>
                    <p> 
                        <img src= "${System.getProperty("user.dir")}${File.separator}graphics${File.separator}Contenedores-Por-Distrito.png"/>
                    </p>

                    </p>

                    <h4>Media de las toneladas anuales de recogidas por cada tipo de basura agrupado por distrito: $mediaToneladasAnuales</h4>

                    <p>
                        <h4>Media de las toneladas mensuales de recogida por distrito</h4>
                        <p> 
                        <img src= "${System.getProperty("user.dir")}${File.separator}graphics${File.separator}Media-Toneladas-Mensuales-Distrito.png">
                    </p>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación de toneladas anuales de recogida por cada tipo de basura agrupadas por distrito</h5>
                            <p>$MaxMinMeanStd</p>
                        </h4>
                    </p>
                    <h4>Suma de todo lo recogido en un año por distrito $sumaRecogidoDistrito </h4>
                    <h4>Cantidad de cada tipo de residuo recogida por distrito</h4>
                    <p id="fecha">Tiempo de generación del mismo en milisegundos: <i>${(System.currentTimeMillis() - initialExecutionTimeMillis)}</i></p>
                </div>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Autores: Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    <strong>${Util.getCurrentDateTimeSpanishFormat()}</strong>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    private fun generateDistrictSummary(): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="$css"/>
                
                <style type="text/css"/>
                <title>Práctica Acceso a Datos 01</title>
            </head>
            <body>
                <!--Archivo "Resumen_distrito"-->
                <h1 align="center" >Historial de recogida de basura y reciclaje en ${distrito}</h1>
                <br>
                <div id="contenedor">
                    <h5>Contenedores por tipo: </h5>
                    <p>$contenedoresDistrito</p>

                    <br>
                    <h5>Toneladas de residuo totales recogidas</h5>
                    <p>(inserte $sumaRecogidoDistrito)</p>

                    <p>
                        <h4>Toneladas totales de residuo recogidas</h4>
                        <p>
                            <img src= "${System.getProperty("user.dir")}${File.separator}graphics${File.separator}Toneladas-Totales-Tipo-Residuo.png"/>
                        </p>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación por mes por residuo en $MaxMinMeanStd</h4>
                        
                        
                
                        <h4>Máximo, mínimo y media por meses</h4>
                        <p>
                            <img src= "${System.getProperty("user.dir")}${File.separator}graphics${File.separator}Max-Min-Media-Desviacion-Distrito.png"/>
                        </p>
                    </p>
                    <p align="right" id="fecha">Tiempo de generación del mismo en milisegundos: <i>${(System.currentTimeMillis() - initialExecutionTimeMillis)}</i></p>
                </div>
                <br>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    ${Util.getCurrentDateTimeSpanishFormat()}
                </div>
                
                
            </body>
            </html>
        """.trimIndent()
    }

    private fun generateHTML(generateSummary: String, destinationDirectory: String, filtered: Boolean) {
        val processedPath = if (!filtered) {
            Paths.get("${destinationDirectory}${File.separator}resumen.html")
        } else {
            Paths.get("${destinationDirectory}${File.separator}resumen_${distrito}.html")
        }
        val html = processedPath.toFile()
        if (!html.exists()) {
            try {
                val dDirectory = File(destinationDirectory)
                if (!dDirectory.exists()) {
                    dDirectory.mkdirs()
                }
                if (!dDirectory.isDirectory) {
                    println("$destinationDirectory is not a directory.")
                    exitProcess(1707)
                }
                FileWriter(html).use { writer ->
                    writer.write(generateSummary)
                    Desktop.getDesktop().browse(processedPath.toUri())
                }
            } catch (e: Exception) {
                println("Error al crear el Html: ${e.message}")
            }
        } else {
            println("${html.name} already exists, would you like to overwrite the file? [y/n]")
            var answer = ""
            while (!answer.equals("y", ignoreCase = true) &&
                !answer.equals("n", ignoreCase = true)
            ) {
                answer = readLine().toString()
            }
            if (answer.equals("y", ignoreCase = true)) {
                val deleted: Boolean = html.delete()
                if (!deleted) {
                    println("Unable to delete ${html.name}")
                    exitProcess(1777)
                } else {
                    generateHTML(generateSummary, destinationDirectory, filtered)
                }
            } else {
                exitProcess(0)
            }
        }
    }
}