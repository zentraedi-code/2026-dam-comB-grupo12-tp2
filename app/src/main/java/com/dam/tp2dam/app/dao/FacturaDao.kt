package com.dam.tp2dam.app.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.dam.tp2dam.app.domain.Factura
import com.dam.tp2dam.app.domain.SocioVencido
import java.util.Date

class FacturaDao(private val db: SQLiteDatabase) {

    // INSERTAR FACTURA
    fun insertar(factura: Factura): Long {
        val valores = ContentValues().apply {
            put("usuarioId", factura.usuarioId)
            put("fecha_vencimiento", factura.fechaVencimiento.time)
            factura.fechaPago?.time?.let { put("fecha_pago", it) }
            put("importe", factura.importe)
        }
        return db.insert("factura", null, valores)
    }

    // LISTA DE SOCIOS CON FACTURA VENCIDA
    fun obtenerSociosVencidos(): List<SocioVencido> {
        val lista = mutableListOf<SocioVencido>()

        val cursor = db.rawQuery(
            """
            SELECT c.dni, c.nombre, c.apellido, c.telefono, f.importe, f.fecha_vencimiento
            FROM cliente c 
            INNER JOIN factura f ON c.id = f.usuarioId
            WHERE f.fecha_vencimiento < ?
              AND f.fecha_pago IS NULL
              AND c.tipo_cliente = 'SOCIO'
            """.trimIndent(),
            arrayOf(System.currentTimeMillis().toString())
        )

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    SocioVencido(
                        dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                        telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                        importe = cursor.getDouble(cursor.getColumnIndexOrThrow("importe")),
                        fechaVencimiento = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha_vencimiento")))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    // CANTIDAD DE SOCIOS CON FACTURA VENCIDA
    fun obtenerCantidadSociosVencidos(): Int {
        val cursor = db.rawQuery(
            """
            SELECT COUNT(DISTINCT f.usuarioId)
            FROM factura f 
            INNER JOIN cliente c ON c.id = f.usuarioId
            WHERE f.fecha_vencimiento < ?
              AND f.fecha_pago IS NULL
              AND c.tipo_cliente = 'SOCIO'
            """.trimIndent(),
            arrayOf(System.currentTimeMillis().toString())
        )

        val cantidad = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        return cantidad
    }
}
