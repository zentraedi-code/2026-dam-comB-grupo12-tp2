package com.dam.tp2dam.app.dao

import Cliente
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class ClienteDao(private val db: SQLiteDatabase) {

    fun insertar(cliente: Cliente): Long {
        val valores = ContentValues().apply {
            put("dni", cliente.dni)
            put("nombre", cliente.nombre)
            put("apellido", cliente.apellido)
            put("telefono", cliente.telefono)
            put("email", cliente.email)
            put("habilitado", if (cliente.habilitado) 1 else 0)
            put("es_socio", if (cliente.esSocio) 1 else 0)
            put("fecha_alta", cliente.fechaAlta)
        }
        return db.insert("cliente", null, valores)
    }

    fun obtenerHabilitados(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val cursor = db.rawQuery(
            "Select * from cliente where habilitado = 1",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    habilitado = cursor.getInt(cursor.getColumnIndexOrThrow("habilitado")) == 1,
                    esSocio = cursor.getInt(cursor.getColumnIndexOrThrow("es_socio")) == 1,
                    fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta"))
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }

        cursor.close();

        return clientes
    }

    fun eliminar(dni: String): Int {
        val valores = ContentValues().apply {
            put("habilitado", 0)
        }
        return db.update("cliente", valores, "dni = ?", arrayOf(dni))
    }

    fun obtenerCantidadSocios(): Int {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM cliente WHERE es_socio = 1 AND habilitado = 1", null)

        var cantidad = 0

        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0)
        }

        cursor.close()
        return cantidad
    }

    fun obtenerCantidadNoSocios(): Int {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM cliente WHERE es_socio = 0 AND habilitado = 1", null)

        var cantidad = 0

        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0)
        }

        cursor.close()
        return cantidad
    }
}

