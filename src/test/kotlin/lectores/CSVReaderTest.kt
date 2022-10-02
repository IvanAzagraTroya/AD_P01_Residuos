package lectores

import model.Residuos
import model.TipoResiduo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.io.File

import model.Contenedor
import model.TipoContenedor

class CSVReaderTest {
    val csvResiduosName = "data${File.separator}residuos_test.csv"
    val csvContenedoresTest = "data${File.separator}contenedores_test.csv"
    val expectedResiduosList = listOf(
        Residuos(año = "2021", mes = "enero", lote = 1, tipoResiduo = TipoResiduo.RESTO, distrito = 1, nombreDistrito = "Centro", toneladas = 3477.92),
        Residuos(año = "2021", mes = "febrero", lote = 3, tipoResiduo = TipoResiduo.ENVASES, distrito = 11, nombreDistrito = "Carabanchel", toneladas = 695.64),
        Residuos(año = "2021", mes = "marzo", lote = 2, tipoResiduo = TipoResiduo.VIDRIO_COMERCIAL, distrito = 4, nombreDistrito = "Salamanca", toneladas = 45.42),
        Residuos(año = "2021", mes = "abril", lote = 1, tipoResiduo = TipoResiduo.ORGANICA, distrito = 9, nombreDistrito = "Moncloa - Aravaca", toneladas = 1008.18),
    )

    val csvFile = "data"+ File.separator + "contenedores_test.csv"

    val expectedContenedoresList = listOf(
        Contenedor("182476",TipoContenedor.PAPEL_Y_CARTON, "IG_17","PC 3000 CAMPANA PE",1,3,"VILLAVERDE","","CALLE", "DE FLORA TRISTAN","5","CALLE DE FLORA TRISTAN, 5"),
        Contenedor("182493",TipoContenedor.PAPEL_Y_CARTON,"IG_2","PC 3000 CAMPANA PE",1,3,"ARGANZUELA","","CALLE","DE FRAY LUIS DE LEON","1","CALLE DE FRAY LUIS DE LEON, 1"),
        Contenedor("182993",TipoContenedor.PAPEL_Y_CARTON,"IG_2","PC 3000 CAMPANA PE",1,3,"ARGANZUELA","","CALLE","DE MENDEZ ALVARO","77","CALLE DE MENDEZ ALVARO, 77"),
        Contenedor("179018",TipoContenedor.ENVASES,"CL_17","Env 3200 CL",1,3,"VILLAVERDE","","CALLE","DEL URANIO","2","CALLE DEL URANIO, 2"),
        Contenedor("178986",TipoContenedor.RESTO,"CL_12","Res 3200 CL",1,3,"USERA","","CALLE","DE ANTONIO LOPEZ","181","CALLE DE ANTONIO LOPEZ, 181")
    )

    @Test
    fun readCSVResiduosWorksOK() {
        val res = CSVReader.readCSVResiduos(csvResiduosName)

        assertAll(
            { assertEquals(4, res.size) },
            { assertEquals(expectedResiduosList.toString(), res.toString()) }
        )
    }

    @Test
    fun readCSVResiduosdevuelveExcepcion() {
        val res = assertThrows<IllegalArgumentException> { CSVReader.readCSVResiduos("uwu") }

        assertEquals("File uwu does not exist.", res.message)
    }

    @Test
    fun readCSVContenedoresTestOk() {
        val csvFile = CSVReader.readCSVContenedores(csvContenedoresTest)

        assertAll(
            { assertEquals(11, csvFile.size)},
            { assertEquals(expectedContenedoresList.toString(), csvFile.toString()) }
        )
    }

    @Test
    fun readCsvContenedoresException() {
        val res = assertThrows<IllegalArgumentException> { CSVReader.readCSVContenedores("Content") }

        assertEquals("File content does not exist.", res.message)
    }
}