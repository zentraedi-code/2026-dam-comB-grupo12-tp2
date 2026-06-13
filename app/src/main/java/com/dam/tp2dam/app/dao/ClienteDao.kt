package com.dam.tp2dam.app.dao

import Cliente
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class ClienteDao(private val db: SQLiteDatabase) {

    fun insertar(cliente: Cliente): Long {
        val values = ContentValues().apply {
            put("dni", cliente.dni)
            put("nombre", cliente.nombre)
            put("apellido", cliente.apellido)
            put("telefono", cliente.telefono)
            put("email", cliente.email)
            put("habilitado", if (cliente.habilitado) 1 else 0)
            put("es_socio", if (cliente.esSocio) 1 else 0)
            put("fecha_alta", cliente.fechaAlta)
            put("tipo_cliente", cliente.tipoCliente)
            put(
                "apto_fisico",
                when (cliente.aptoFisico) {
                    true -> 1
                    false -> 0
                    null -> null
                }
            )
        }
        return db.insert("cliente", null, values)
    }

    fun actualizar(cliente: Cliente): Int {
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("apellido", cliente.apellido)
            put("telefono", cliente.telefono)
            put("email", cliente.email)
            put("habilitado", if (cliente.habilitado) 1 else 0)
            put("es_socio", if (cliente.esSocio) 1 else 0)
            put("fecha_alta", cliente.fechaAlta)
            put("tipo_cliente", cliente.tipoCliente)
            put(
                "apto_fisico",
                when (cliente.aptoFisico) {
                    true -> 1
                    false -> 0
                    null -> null
                }
            )
        }
        return db.update("cliente", values, "dni = ?", arrayOf(cliente.dni))
    }

    fun obtenerTodos(): List<Cliente> {
        val lista = mutableListOf<Cliente>()
        val cursor = db.rawQuery("SELECT * FROM cliente", null)

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
                    fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
                    tipoCliente = cursor.getString(cursor.getColumnIndexOrThrow("tipo_cliente")),
                    aptoFisico = when (cursor.getInt(cursor.getColumnIndexOrThrow("apto_fisico"))) {
                        1 -> true
                        0 -> false
                        else -> null
                    }
                )
                lista.add(cliente)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    fun buscarPorDni(dni: String): Cliente? {
        val cursor = db.rawQuery(
            "SELECT * FROM cliente WHERE dni = ?",
            arrayOf(dni)
        )

        if (cursor.moveToFirst()) {
            val cliente = Cliente(
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                habilitado = cursor.getInt(cursor.getColumnIndexOrThrow("habilitado")) == 1,
                esSocio = cursor.getInt(cursor.getColumnIndexOrThrow("es_socio")) == 1,
                fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                tipoCliente = cursor.getString(cursor.getColumnIndexOrThrow("tipo_cliente")),
                aptoFisico = when (cursor.getInt(cursor.getColumnIndexOrThrow("apto_fisico"))) {
                    1 -> true
                    0 -> false
                    else -> null
                }
            )
            cursor.close()
            return cliente
        }

        cursor.close()
        return null
    }

    fun contarSocios(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM cliente WHERE tipo_cliente = 'SOCIO' AND habilitado = 1 ",
            null
        )
        cursor.moveToFirst()
        val cantidad = cursor.getInt(0)
        cursor.close()
        return cantidad
    }

    fun contarNoSocios(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM cliente WHERE tipo_cliente = 'NO_SOCIO'  AND habilitado = 1 ",
            null
        )
        cursor.moveToFirst()
        val cantidad = cursor.getInt(0)
        cursor.close()
        return cantidad
    }

    fun puedeImprimirCarnet(cliente: Cliente): Boolean {
        val cursor = db.rawQuery(
            "SELECT 1 FROM factura f INNER JOIN cliente c ON c.id = f.usuarioId " +
                    "WHERE c.dni = ? AND (c.habilitado = 0 OR ( f.fecha_vencimiento < ? AND f.fecha_pago IS NULL))",
            arrayOf(
                cliente.dni,
                System.currentTimeMillis().toString()
            )
        )

        val tieneFacturaVencida = cursor.moveToFirst()

        cursor.close()

        return !tieneFacturaVencida
    }
}

