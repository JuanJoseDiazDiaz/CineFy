package com.example.cinefy.ui.screens.profileScreen

data class ProfileUiState(
    val nameUser: String = "",
    val passwordUser: String = "",
    val modoDeVisualizacionPantalla : ModoVisualizacionPantalla = ModoVisualizacionPantalla.SISTEMA,
    val errorMessage: String? = null,
)
enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
}
enum class ModoVisualizacionPantalla(val modoVisualizacion: String) {
    CLARO("LIGHT"),
    OSCURO("DARK"),
    SISTEMA("SYSTEM")
}