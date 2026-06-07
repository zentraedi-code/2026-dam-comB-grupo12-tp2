package com.dam.tp2dam.app.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "club.db", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE cliente(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni TEXT,
                nombre TEXT,
                apellido TEXT,
                telefono TEXT,
                email TEXT,
                habilitado INTEGER,
                es_socio INTEGER,
                fecha_alta INTEGER
            )
            """
        )

        db.execSQL(
            """
            CREATE TABLE usuario(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT,
                clave TEXT
            )
            """
        )

        // Unico usuario admin/123456. No se hace abm de esta tabla
        val valores = ContentValues().apply {
            put("usuario", "admin")
            put("clave", "123456")
        }
        db.insert("usuario", null, valores)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun getClienteDao(): ClienteDao {
        return ClienteDao(writableDatabase)
    }

    fun getUsuarioDao(): UsuarioDao {
        return UsuarioDao(writableDatabase)
    }

}
