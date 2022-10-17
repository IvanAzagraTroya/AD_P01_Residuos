package lectores

import model.Contenedor
import model.Residuos
import model.TipoContenedor
import model.TipoResiduo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.io.File

class CSVReaderTest {
    private val csvResiduosName = "dataTest${File.separator}residuos_test.csv"
    private val csvContenedoresTest = "dataTest${File.separator}contenedores_test.csv"

    private val expectedResiduosList = listOf(
        Residuos(año = "2021", mes = "enero", lote = 1, tipoResiduo = TipoResiduo.RESTO, distrito = 1, nombreDistrito = "Centro", toneladas = 3477.92),
        Residuos(año = "2021", mes = "febrero", lote = 3, tipoResiduo = TipoResiduo.ENVASES, distrito = 11, nombreDistrito = "Carabanchel", toneladas = 695.64),
        Residuos(año = "2021", mes = "marzo", lote = 2, tipoResiduo = TipoResiduo.VIDRIO_COMERCIAL, distrito = 4, nombreDistrito = "Salamanca", toneladas = 45.42),
        Residuos(año = "2021", mes = "abril", lote = 1, tipoResiduo = TipoResiduo.ORGANICA, distrito = 9, nombreDistrito = "Moncloa - Aravaca", toneladas = 1008.18),
    )

    private val expectedContenedoresList = listOf(
        Contenedor("182476",TipoContenedor.PAPEL_Y_CARTON, "IG_17","PC 3000 CAMPANA PE",1,3,"VILLAVERDE","N/A","CALLE", "DE FLORA TRISTAN","5"),
        Contenedor("182493",TipoContenedor.PAPEL_Y_CARTON,"IG_2","PC 3000 CAMPANA PE",1,3,"ARGANZUELA","N/A","CALLE","DE FRAY LUIS DE LEON","1"),
        Contenedor("182993",TipoContenedor.PAPEL_Y_CARTON,"IG_2","PC 3000 CAMPANA PE",1,3,"ARGANZUELA","N/A","CALLE","DE MENDEZ ALVARO","77"),
        Contenedor("179018",TipoContenedor.ENVASES,"CL_17","Env 3200 CL",1,3,"VILLAVERDE","N/A","CALLE","DEL URANIO","2"),
        Contenedor("178986",TipoContenedor.RESTO,"CL_12","Res 3200 CL",1,3,"USERA","N/A","CALLE","DE ANTONIO LOPEZ","181")
    )

    @Test
    fun readCSVResiduosWorksOK() {
        val res = CSVReader.readCSVResiduos(csvResiduosName, ";")

        assertAll(
            { assertEquals(4, res.size) },
            { assertEquals(expectedResiduosList.toString(), res.toString()) }
        )
    }

    @Test
    fun readCSVResiduosThrowsException1() {
        val res = assertThrows<Exception> { CSVReader.readCSVResiduos("uwu", ";") }

        assertEquals("File uwu does not exist.", res.message)
    }

    @Test
    fun readCSVResiduosThrowsException2() {
        val res = assertThrows<Exception> { CSVReader.readCSVResiduos("dataTest${File.separator}bad_format.csv", ";") }

        assertEquals("File bad_format.csv has an incorrect format.", res.message)
    }

    @Test
    fun readCSVResiduosThrowsException3() {
        val res = assertThrows<Exception> { CSVReader.readCSVResiduos("dataTest${File.separator}emptyCSV.csv", ";") }

        assertEquals("File emptyCSV.csv is empty. Use a valid CSV file.", res.message)
    }

    @Test
    fun readCSVResiduosThrowsException4() {
        val res = assertThrows<Exception> { CSVReader.readCSVResiduos("dataTest${File.separator}not_a_csv.txt", ";") }

        assertEquals("File not_a_csv.txt is not a csv file.", res.message)
    }

    @Test
    fun readCSVResiduosThrowsException5() {
        val res = assertThrows<Exception> { CSVReader.readCSVResiduos("dataTest${File.separator}oneLineResiduos.csv", ";") }

        assertEquals("File dataTest${File.separator}oneLineResiduos.csv's content is empty. Use a valid csv file.", res.message)
    }

    @Test
    fun readCSVContenedoresTestOk() {
        val csvFile = CSVReader.readCSVContenedores(csvContenedoresTest, ";")

        assertAll(
            { assertEquals(5, csvFile.size)},
            { assertEquals(expectedContenedoresList.toString().trim(), csvFile.toString().trim()) }
        )
    }

    @Test
    fun readCSVContenedoresThrowsException1() {
        val res = assertThrows<Exception> { CSVReader.readCSVContenedores("content", ";") }

        assertEquals("File content does not exist.", res.message)
    }

    @Test
    fun readCSVContenedoresThrowsException2() {
        val res = assertThrows<Exception> { CSVReader.readCSVContenedores("dataTest${File.separator}bad_format.csv", ";") }

        assertEquals("File bad_format.csv has an incorrect format.", res.message)
    }

    @Test
    fun readCSVContenedoresThrowsException3() {
        val res = assertThrows<Exception> { CSVReader.readCSVContenedores("dataTest${File.separator}emptyCSV.csv", ";") }

        assertEquals("File emptyCSV.csv is empty. Use a valid CSV file.", res.message)
    }

    @Test
    fun readCSVContenedoresThrowsException4() {
        val res = assertThrows<Exception> { CSVReader.readCSVContenedores("dataTest${File.separator}not_a_csv.txt", ";") }

        assertEquals("File not_a_csv.txt is not a csv file.", res.message)
    }

    @Test
    fun readCSVContenedoresThrowsException5() {
        val res = assertThrows<Exception> { CSVReader.readCSVContenedores("dataTest${File.separator}oneLineContenedores.csv", ";") }

        assertEquals("File dataTest${File.separator}oneLineContenedores.csv's content is empty. Use a valid csv file.", res.message)
    }
}