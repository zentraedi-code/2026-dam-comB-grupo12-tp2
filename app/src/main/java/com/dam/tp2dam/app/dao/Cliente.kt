package com.dam.tp2dam.app.domain

data class Cliente(
    val id: Int = 0,
    val dni: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String,
    val tipoCliente: String,   // "SOCIO" o "NO_SOCIO"
    val aptoFisico: Boolean?   // true/false si es SOCIO, null si es NO_SOCIO
)
