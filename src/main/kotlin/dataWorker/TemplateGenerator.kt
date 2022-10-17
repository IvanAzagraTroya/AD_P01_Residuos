package dataWorker

class TemplateGenerator(
    val distrito: String,
    val contenedoresDistrito: Int,
    val toneladasDistritoResiduoTotales: Int,
    val maximoResiduos: String,
    val minimoResiduos: String,
    val mediaResiduos: String,
    val desviaciónResiduos: String,

    val mediaContenedoresTipo: Int,
    val mediaContenedoresDistrito: Int,
    val mediaToneladasAnuales: String,
    val recogidaDistrito: String,
    val tipoResiduoDistrito: String,
    val maximo: String,
    val minimo: String,
    val media: String,
    val desviacion: String,

    val tiempoGeneracion: Long,
    val fechaGeneracion: String,
) {
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
                    <p>$mediaContenedoresTipo</p>

                    <br>
                    <h4>Media de contenedores de cada tipo por distrito</h4>
                    <p>$mediaContenedoresDistrito</p>

                    <p>
                        <h4>(_gráfico_) Contenedores totales por distrito</h4>

                    </p>

                    <h4>Media de las toneladas anuales de recogidas por cada tipo de basura agrupado por distrito: $mediaToneladasAnuales</h4>

                    <p>
                        <h4>(_gráfico_) Media de las toneladas mensuales de recogida por distrito</h4>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación de toneladas anuales de recogida por cada tipo de basura agrupadas por distrito</h5>
                        <i>Máximo: $maximo</i>
                        <br>
                        <i>Mínimo: $minimo</i>
                        <br>
                        <i>Media: $media</i>
                        <br>
                        <i>Desviación: $desviacion</i>
                        </h4>
                    </p>
                    <h4>Suma de todo lo recogido en un año por distrito</h4>
                    <h4>Cantidad de cada tipo de residuo recogida por distrito</h4>
                    <p align="right">Tiempo de generación del mismo en milisegundos: <i>$tiempoGeneracion</i></p>
                </div>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    $fechaGeneracion
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
                <h1 align="center" >Historial de recogida de basura y reciclaje de $distrito </h1>
                <br>
                <div id="contenedor">
                    <h5>Contenedores por tipo en $distrito: </h5>
                    <p>$contenedoresDistrito</p>

                    <br>
                    <h5>Toneladas de residuo totales recogidas en $distrito</h5>
                    <p>(inserte $toneladasDistritoResiduoTotales)</p>

                    <p>
                        <h4>(_gráfico_) Toneladas totales de residuo recogidas en $distrito</h4>

                    </p>

                    <p align="left">
                        <h4>Máximo, mínimo, media y desviación por mes por residuo en $distrito</h5>
                        <i>Máximo: $maximoResiduos</i>
                        <br>
                        <i>Mínimo: $minimoResiduos</i>
                        <br>
                        <i>Media: $mediaResiduos</i>
                        <br>
                        <i>Desviación: $desviaciónResiduos</i>
                        </h4>
                
                        <h4>(_gráfico_)Máximo, mínimo y media por meses en $distrito</h4>
                    </p>
                    <p align="right">Tiempo de generación del mismo en milisegundos: <i>$tiempoGeneracion</i></p>
                </div>
                <br>
                <!-- los id son para definirlos en la misma línea del html generado a través del css3-->
                <div id="nombres">
                    <strong>Iván Azagra y Daniel Rodríguez</strong>
                </div>
                <div id="fecha">
                    $fechaGeneracion
                </div>
                
                
            </body>
            </html>
        """.trimIndent()
    }
}