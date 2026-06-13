package com.dam.tp2dam.app.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.dam.tp2dam.app.domain.Cliente

class ClienteDao(private val db: SQLiteDatabase) {

    // INSERTAR CLIENTE
    fun insertar(cliente: Cliente): Long {
        val values = ContentValues().apply {
            put("dni", cliente.dni)
            put("nombre", cliente.nombre)
            put("apellido", cliente.apellido)
            put("telefono", cliente.telefono)
            put("email", cliente.email)
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

    // OBTENER TODOS LOS CLIENTES
    fun obtenerTodos(): List<Cliente> {
        val lista = mutableListOf<Cliente>()
        val cursor = db.rawQuery("SELECT * FROM cliente", null)

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
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

    // OBTENER SOLO SOCIOS
    fun obtenerSocios(): List<Cliente> {
        return obtenerPorTipo("SOCIO")
    }

    // OBTENER SOLO NO SOCIOS
    fun obtenerNoSocios(): List<Cliente> {
        return obtenerPorTipo("NO_SOCIO")
    }

    private fun obtenerPorTipo(tipo: String): List<Cliente> {
        val lista = mutableListOf<Cliente>()
        val cursor = db.rawQuery(
            "SELECT * FROM cliente WHERE tipo_cliente = ?",
            arrayOf(tipo)
        )

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
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

    // BUSCAR POR DNI
    fun buscarPorDni(dni: String): Cliente? {
        val cursor = db.rawQuery(
            "SELECT * FROM cliente WHERE dni = ?",
            arrayOf(dni)
        )

        if (cursor.moveToFirst()) {
            val cliente = Cliente(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
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

    // ELIMINAR CLIENTE POR DNI
    fun eliminar(dni: String): Int {
        return db.delete("cliente", "dni = ?", arrayOf(dni))
    }

    // CONTAR SOCIOS
    fun contarSocios(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM cliente WHERE tipo_cliente = 'SOCIO'",
            null
        )
        cursor.moveToFirst()
        val cantidad = cursor.getInt(0)
        cursor.close()
        return cantidad
    }

    // CONTAR NO SOCIOS
    fun contarNoSocios(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM cliente WHERE tipo_cliente = 'NO_SOCIO'",
            null
        )
        cursor.moveToFirst()
        val cantidad = cursor.getInt(0)
        cursor.close()
        return cantidad
    }
}

