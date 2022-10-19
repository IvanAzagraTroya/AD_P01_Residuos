package model

/**
 * @author Iván Azagra Troya
 * Enumerador con los tipos de contenedores que pueden haber
 */
enum class TipoContenedor(tipo: String) {
    ORGANICA("ORGANICA"),
    RESTO("RESTO"),
    ENVASES("ENVASES"),
    VIDRIO("VIDRIO"),
    PAPEL_Y_CARTON("PAPEL_Y_CARTON")

}