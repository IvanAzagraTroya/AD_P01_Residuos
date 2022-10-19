package dataWorker

import DTO.ExecutionDTO
import java.awt.Desktop
import java.io.File
import java.io.FileWriter
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

/**
 * @author Iván Azagra Troya y Daniel Rodríguez Muñoz
 * Clase generadora del template en el que se muestran los datos
 * @param executionDTO DTO utilizado para pasar los valores necesarios para rellenar el HTML
 */
class TemplateGenerator(private val executionDTO: ExecutionDTO, private val destinationDirectory: String, private val initialExecutionTimeMillis: Long, districtName: String?) {
    private val processedPath: Path = if (districtName != null) {
        Paths.get("$destinationDirectory${File.separator}resumen_$districtName.html")
    } else {
        Paths.get("$destinationDirectory${File.separator}resumen.html")
    }
    private val pathToImages: Path = Paths.get("${System.getProperty("user.dir")}${File.separator}graphics")

    init {
        generateHTML(executionDTO, processedPath, initialExecutionTimeMillis, pathToImages, districtName)
    }

    private fun generateHTML(executionDTO: ExecutionDTO, processedPath: Path, initialExecutionTimeMillis: Long, pathToImages: Path, districtName: String?) {
        val html = processedPath.toFile()
        if (!html.exists()) {
            val graphics = pathToImages.toFile()
            if (!graphics.exists()) {
                graphics.mkdirs()
            }
            val writer = FileWriter(html)
            try {
                writer.write(writeHTML(executionDTO, pathToImages, initialExecutionTimeMillis, districtName))
                Desktop.getDesktop().browse(processedPath.toUri())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                writer.close()
            }
        } else {
            println("${html.name} already exists. would you like to overwrite it? [y/n]")
            var result = ""
            while (!(result.contentEquals("y")) && !(result.contentEquals("n"))) {
                result = readLine().toString()
            }
            if (result.contentEquals("y")) {
                val deleted = html.delete()
                if (deleted) {
                    generateHTML(executionDTO, processedPath, initialExecutionTimeMillis, pathToImages, districtName)
                } else {
                    println("Unable to delete ${html.name}")
                    exitProcess(1777)
                }
            }
            else {
                exitProcess(0)
            }
        }
    }

    private fun writeHTML(executionDTO: ExecutionDTO, pathToImages: Path, initialExecutionTimeMillis: Long, districtName: String?): String {
        val result = """
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
                <h1 align="center">Resumen de recogidas de basura y reciclaje de ${executionDTO.distrito} </h1>

                <br>
        """.trimIndent()
        if (districtName == null) {

        }

        /*
                <div id="nombres">
                    Html genetado por: <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    ${executionDTO.fechaGeneracion}
                </div>
            </body>
            </html>
        """.trimIndent()
         */
    }

    /*

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
                    <p>${executionDTO.mediaContenedoresTipo}</p>

                    <br>
                    <h4>Media de contenedores de cada tipo por distrito</h4>
                    <p>${executionDTO.mediaContenedoresDistrito}</p>

                    <p>
                        <h4>(_gráfico_) Contenedores totales por distrito</h4>

                    </p>

                    <h4>Media de las toneladas anuales de recogidas por cada tipo de basura agrupado por distrito: ${executionDTO.mediaToneladasAnuales}</h4>

                    <p>
                        <h4>(_gráfico_) Media de las toneladas mensuales de recogida por distrito</h4>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación de toneladas anuales de recogida por cada tipo de basura agrupadas por distrito</h5>
                        <i>Máximo: ${executionDTO.maximo}</i>
                        <br>
                        <i>Mínimo: ${executionDTO.minimo}</i>
                        <br>
                        <i>Media: ${executionDTO.media}</i>
                        <br>
                        <i>Desviación: ${executionDTO.desviacion}</i>
                        </h4>
                    </p>
                    <h4>Suma de todo lo recogido en un año por distrito</h4>
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
                <h1 align="center" >Historial de recogida de basura y reciclaje de ${executionDTO.distrito} </h1>
                <br>
                <div id="contenedor">
                    <h5>Contenedores por tipo en ${executionDTO.distrito}: </h5>
                    <p>${executionDTO.contenedoresDistrito}</p>

                    <br>
                    <h5>Toneladas de residuo totales recogidas en ${executionDTO.distrito}</h5>
                    <p>(inserte ${executionDTO.toneladasDistritoResiduoTotales})</p>

                    <p>
                        <h4>(_gráfico_) Toneladas totales de residuo recogidas en ${executionDTO.distrito}</h4>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación por mes por residuo en ${executionDTO.distrito}</h5>
                        <i>Máximo: ${executionDTO.maximoResiduos}</i>
                        <br>
                        <i>Mínimo: ${executionDTO.minimoResiduos}</i>
                        <br>
                        <i>Media: ${executionDTO.mediaResiduos}</i>
                        <br>
                        <i>Desviación: ${executionDTO.desviaciónResiduos}</i>
                        </h4>
                
                        <h4>(_gráfico_)Máximo, mínimo y media por meses en ${executionDTO.distrito}</h4>
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
    
     */
}