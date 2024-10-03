package com.marceloacuna.myappsemana9.Model

import kotlinx.serialization.Serializable

@Serializable
data class User_Model (
    val email:String = "",
    val contraseña: String = "",
)