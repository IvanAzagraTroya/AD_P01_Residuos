@file:Suppress("UNCHECKED_CAST", "USELESS_CAST")
package model
import org.jetbrains.kotlinx.dataframe.annotations.*
import org.jetbrains.kotlinx.dataframe.ColumnsContainer
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.columns.ColumnGroup

val ColumnsContainer<model.Contenedor>.barrio: DataColumn<String> @JvmName("Contenedor_barrio") get() = this["barrio"] as DataColumn<String>
val DataRow<model.Contenedor>.barrio: String @JvmName("Contenedor_barrio") get() = this["barrio"] as String
val ColumnsContainer<model.Contenedor>.cantidad: DataColumn<Int> @JvmName("Contenedor_cantidad") get() = this["cantidad"] as DataColumn<Int>
val DataRow<model.Contenedor>.cantidad: Int @JvmName("Contenedor_cantidad") get() = this["cantidad"] as Int
val ColumnsContainer<model.Contenedor>.codigoSituado: DataColumn<String> @JvmName("Contenedor_codigoSituado") get() = this["codigoSituado"] as DataColumn<String>
val DataRow<model.Contenedor>.codigoSituado: String @JvmName("Contenedor_codigoSituado") get() = this["codigoSituado"] as String
val ColumnsContainer<model.Contenedor>.descripcionModelo: DataColumn<String> @JvmName("Contenedor_descripcionModelo") get() = this["descripcionModelo"] as DataColumn<String>
val DataRow<model.Contenedor>.descripcionModelo: String @JvmName("Contenedor_descripcionModelo") get() = this["descripcionModelo"] as String
val ColumnsContainer<model.Contenedor>.distrito: DataColumn<String> @JvmName("Contenedor_distrito") get() = this["distrito"] as DataColumn<String>
val DataRow<model.Contenedor>.distrito: String @JvmName("Contenedor_distrito") get() = this["distrito"] as String
val ColumnsContainer<model.Contenedor>.lote: DataColumn<Int> @JvmName("Contenedor_lote") get() = this["lote"] as DataColumn<Int>
val DataRow<model.Contenedor>.lote: Int @JvmName("Contenedor_lote") get() = this["lote"] as Int
val ColumnsContainer<model.Contenedor>.modelo: DataColumn<String> @JvmName("Contenedor_modelo") get() = this["modelo"] as DataColumn<String>
val DataRow<model.Contenedor>.modelo: String @JvmName("Contenedor_modelo") get() = this["modelo"] as String
val ColumnsContainer<model.Contenedor>.nombreCalle: DataColumn<String> @JvmName("Contenedor_nombreCalle") get() = this["nombreCalle"] as DataColumn<String>
val DataRow<model.Contenedor>.nombreCalle: String @JvmName("Contenedor_nombreCalle") get() = this["nombreCalle"] as String
val ColumnsContainer<model.Contenedor>.numero: DataColumn<String> @JvmName("Contenedor_numero") get() = this["numero"] as DataColumn<String>
val DataRow<model.Contenedor>.numero: String @JvmName("Contenedor_numero") get() = this["numero"] as String
val ColumnsContainer<model.Contenedor>.tipoContenedor: DataColumn<model.TipoContenedor> @JvmName("Contenedor_tipoContenedor") get() = this["tipoContenedor"] as DataColumn<model.TipoContenedor>
val DataRow<model.Contenedor>.tipoContenedor: model.TipoContenedor @JvmName("Contenedor_tipoContenedor") get() = this["tipoContenedor"] as model.TipoContenedor
val ColumnsContainer<model.Contenedor>.tipoVia: DataColumn<String> @JvmName("Contenedor_tipoVia") get() = this["tipoVia"] as DataColumn<String>
val DataRow<model.Contenedor>.tipoVia: String @JvmName("Contenedor_tipoVia") get() = this["tipoVia"] as String
