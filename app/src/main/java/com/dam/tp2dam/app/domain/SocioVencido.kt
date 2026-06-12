package com.dam.tp2dam.app.domain

import java.util.Date

data class SocioVencido(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val importe: Double,
    val fechaVencimiento: Date
)