package dataWorker

import DTO.ExecutionDTO

class TemplateGenerator(private val executionDTO: ExecutionDTO) {
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
}