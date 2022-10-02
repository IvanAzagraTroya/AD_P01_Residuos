package lectores

import model.Residuos
import model.TipoResiduo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.File

class CSVReaderTest {
    val csvResiduosName = "data${File.separator}residuos_test.csv"
    val expectedResiduosList = listOf(
        Residuos(año = "2021", mes = "enero", lote = 1, tipoResiduo = TipoResiduo.RESTO, distrito = 1, nombreDistrito = "Centro", toneladas = 3477.92),
        Residuos(año = "2021", mes = "febrero", lote = 3, tipoResiduo = TipoResiduo.ENVASES, distrito = 11, nombreDistrito = "Carabanchel", toneladas = 695.64),
        Residuos(año = "2021", mes = "marzo", lote = 2, tipoResiduo = TipoResiduo.VIDRIO_COMERCIAL, distrito = 4, nombreDistrito = "Salamanca", toneladas = 45.42),
        Residuos(año = "2021", mes = "abril", lote = 1, tipoResiduo = TipoResiduo.ORGANICA, distrito = 9, nombreDistrito = "Moncloa - Aravaca", toneladas = 1008.18),
    )

    @Test
    fun readCSVResiduosWorksOK() {
        val res = CSVReader.readCSVResiduos(csvResiduosName)

        assertAll(
            { assertEquals(4, res.size) },
            { assertEquals(expectedResiduosList.toString(), res.toString()) }
        )
    }
}