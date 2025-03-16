package com.example.cinefy.ui.screens.profileScreen

data class ProfileUiState(
    val nameUser: String = "",
    val passwordUser: String = "",
    val themeLight: String = "light",
    val themeDark: String = "dark",
    val themeSystem: String = "System",
    val errorMessage: String? = null,
)
enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
}