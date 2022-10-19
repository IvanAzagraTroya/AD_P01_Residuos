package dataWorker

import jetbrains.datalore.base.values.Color
import jetbrains.letsPlot.Stat.identity
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import model.Contenedor
import model.Residuos
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.html
import java.io.File
import kotlin.math.roundToInt

/**
 * @author Iván Azagra Troya
 * Clase encargada de procesar los datos de los csv con Dataframes
 * @param contenedorData lista de contenedores obtenida desde el lector del csv
 * @param residuoData lista de residuos obtenida desde el lector del csv
 */

class DataProcessor(val contenedorData: List<Contenedor>, val residuoData: List<Residuos>) {
    private val dir: String = System.getProperty("user.dir")
    private val imagesPath = File(dir+File.separator+"graphics"+File.separator)
    private val contDataframe = contenedorData.toDataFrame()
    private val residuoDataframe = residuoData.toDataFrame()

    lateinit var contenedoresDistrito: String
    lateinit var mediaToneladasAnuales: String
    lateinit var MaxMinMeanStd: String
    lateinit var sumaRecogidoDistrito: String
    lateinit var cantidadTipoRecogido: String


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
        var d = contDataframe.groupBy("distrito").count().toMap()
        val gTotalContenedores: Plot = letsPlot(data = d) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "distrito"
            y = "contendores"
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
            color = Color.DARK_BLUE
        ) {
            x = "mes"
            y = "toneladas"
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

        val gMaxMinMeanStd:Plot = letsPlot(data = d) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "mes"
            y = "toneladas"
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
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "distrito"
            y = "toneladas"
        } + labs(
            x = "distrito",
            y = "toneladas",
            title = "Toneladas totales por tipo de residuo"
        )
        ggsave(gMaxMinMeanStd, "Toneladas-Totales-Tipo-Residuo.png", 1, 2, imagesPath.toString())
    }

    fun generateSummary(): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="style.css">
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
                        <img src= "./graphics/Contenedores-Por-Distrito.png"/>
                    </p>

                    </p>

                    <h4>Media de las toneladas anuales de recogidas por cada tipo de basura agrupado por distrito: $mediaToneladasAnuales</h4>

                    <p>
                        <h4>Media de las toneladas mensuales de recogida por distrito</h4>
                        <p> 
                        <img src= "./graphics/Media-Toneladas-Mensuales-Distrito.png"/>
                    </p>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación de toneladas anuales de recogida por cada tipo de basura agrupadas por distrito</h5>
                            <p>$MaxMinMeanStd</p>
                        </h4>
                    </p>
                    <h4>Suma de todo lo recogido en un año por distrito $sumaRecogidoDistrito </h4>
                    <h4>Cantidad de cada tipo de residuo recogida por distrito</h4>
                    <p align="right">Tiempo de generación del mismo en milisegundos: <i>${executionDTO.tiempoGeneracion}</i></p>
                </div>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    ${executionDTO.fechaGeneracion}
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun generateDistrictSummary(): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="style.css">
                <title>Práctica Acceso a Datos 01</title>
            </head>
            <body>
                <!--Archivo "Resumen_distrito"-->
                <h1 align="center" >Historial de recogida de basura y reciclaje</h1>
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
                            <img src= "./graphics/Toneladas-Totales-Tipo-Residuo.png"/>
                        </p>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación por mes por residuo en $MaxMinMeanStd</h4>
                        
                        
                
                        <h4>(_gráfico_)Máximo, mínimo y media por meses</h4>
                        <p>
                            <img src= "./graphics/Max-Min-Media-Desviacion-Distrito.png"/>
                        </p>
                    </p>
                    <p align="right">Tiempo de generación del mismo en milisegundos: <i>${executionDTO.tiempoGeneracion}</i></p>
                </div>
                <br>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    ${executionDTO.fechaGeneracion}
                </div>
                
                
            </body>
            </html>
        """.trimIndent()
    }

}