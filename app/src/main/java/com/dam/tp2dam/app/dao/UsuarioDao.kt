package com.dam.tp2dam.app.dao

import Usuario
import android.database.sqlite.SQLiteDatabase

class UsuarioDao(private val db: SQLiteDatabase) {

    fun validarLogin(usuario: String, clave: String): Boolean {
        val cursor = db.rawQuery(
            "SELECT * FROM usuario WHERE usuario = ? AND clave = ?",
            arrayOf(usuario, clave)
        )

        val existe = cursor.moveToFirst()
        cursor.close()

        return existe
    }

}
