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
        var result = """
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
        """.trimIndent()
        executionDTO.contenedoresDistrito.keys.forEach {
            result = result.plus("""
                <h2>Numero de contenedores de cada tipo en ${executionDTO.contenedoresDistrito[it]}</h2>
            """.trimIndent())
            
        }


        if (districtName == null) {
            executionDTO.mediaContenedoresDistrito.forEach {

            }
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

}