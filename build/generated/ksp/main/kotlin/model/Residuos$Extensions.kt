@file:Suppress("UNCHECKED_CAST", "USELESS_CAST")
package model
import org.jetbrains.kotlinx.dataframe.annotations.*
import org.jetbrains.kotlinx.dataframe.ColumnsContainer
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.columns.ColumnGroup

val ColumnsContainer<model.Residuos>.distrito: DataColumn<Int> @JvmName("Residuos_distrito") get() = this["distrito"] as DataColumn<Int>
val DataRow<model.Residuos>.distrito: Int @JvmName("Residuos_distrito") get() = this["distrito"] as Int
val ColumnsContainer<model.Residuos>.lote: DataColumn<Int> @JvmName("Residuos_lote") get() = this["lote"] as DataColumn<Int>
val DataRow<model.Residuos>.lote: Int @JvmName("Residuos_lote") get() = this["lote"] as Int
val ColumnsContainer<model.Residuos>.mes: DataColumn<String> @JvmName("Residuos_mes") get() = this["mes"] as DataColumn<String>
val DataRow<model.Residuos>.mes: String @JvmName("Residuos_mes") get() = this["mes"] as String
val ColumnsContainer<model.Residuos>.nombreDistrito: DataColumn<String> @JvmName("Residuos_nombreDistrito") get() = this["nombreDistrito"] as DataColumn<String>
val DataRow<model.Residuos>.nombreDistrito: String @JvmName("Residuos_nombreDistrito") get() = this["nombreDistrito"] as String
val ColumnsContainer<model.Residuos>.tipoResiduo: DataColumn<model.TipoResiduo> @JvmName("Residuos_tipoResiduo") get() = this["tipoResiduo"] as DataColumn<model.TipoResiduo>
val DataRow<model.Residuos>.tipoResiduo: model.TipoResiduo @JvmName("Residuos_tipoResiduo") get() = this["tipoResiduo"] as model.TipoResiduo
val ColumnsContainer<model.Residuos>.toneladas: DataColumn<Double> @JvmName("Residuos_toneladas") get() = this["toneladas"] as DataColumn<Double>
val DataRow<model.Residuos>.toneladas: Double @JvmName("Residuos_toneladas") get() = this["toneladas"] as Double
val ColumnsContainer<model.Residuos>.year: DataColumn<String> @JvmName("Residuos_year") get() = this["year"] as DataColumn<String>
val DataRow<model.Residuos>.year: String @JvmName("Residuos_year") get() = this["year"] as String
