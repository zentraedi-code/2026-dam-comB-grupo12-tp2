package com.dam.tp2dam.app.domain;

import java.util.Date;

data class Factura(
    val usuarioId: Int,
    val fechaVencimiento:Date,
    val fechaPago: Date?,
    val importe: Double
)