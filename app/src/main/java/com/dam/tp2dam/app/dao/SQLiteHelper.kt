package com.dam.tp2dam.app.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "club.db", null, 2) {   // ← versión 2

    override fun onCreate(db: SQLiteDatabase) {

        // ============================
        //   TABLA CLIENTE (NUEVA)
        // ============================
        db.execSQL(
            """
            CREATE TABLE cliente(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni TEXT NOT NULL,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                telefono TEXT NOT NULL,
                email TEXT NOT NULL,
                tipo_cliente TEXT NOT NULL,   -- SOCIO / NO_SOCIO
                apto_fisico INTEGER           -- 1 = sí, 0 = no (solo si es SOCIO)
            )
            """
        )

        // ============================
        //   TABLA USUARIO (LOGIN)
        // ============================
        db.execSQL(
            """
            CREATE TABLE usuario(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT,
                clave TEXT
            )
            """
        )

        // Usuario admin por defecto
        val valores = ContentValues().apply {
            put("usuario", "admin")
            put("clave", "123456")
        }
        db.insert("usuario", null, valores)


        //   TABLA FACTURA

        db.execSQL(
            """
            CREATE TABLE factura(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuarioId INTEGER,
                fecha_vencimiento INTEGER,
                fecha_pago INTEGER,
                importe REAL,
                FOREIGN KEY(usuarioId) REFERENCES cliente(id)
            )
            """
        )

        //   CLIENTES DE PRUEBA

        db.execSQL(
            """
            INSERT INTO cliente
            (dni, nombre, apellido, telefono, email, tipo_cliente, apto_fisico)
            VALUES
            ('25632187', 'Juan', 'Perez', '111111111', 'juan@mail.com', 'SOCIO', 1),
            ('36215798', 'Maria', 'Gomez', '222222222', 'maria@mail.com', 'SOCIO', 1),
            ('32516017', 'Pedro', 'Lopez', '333333333', 'pedro@mail.com', 'NO_SOCIO', NULL),
            ('06321894', 'Ana', 'Martinez', '444444444', 'ana@mail.com', 'SOCIO', 1),
            ('36987125', 'Carlos', 'Suarez', '555555555', 'carlos@mail.com', 'NO_SOCIO', NULL),
            ('42365178', 'Lucia', 'Diaz', '666666666', 'lucia@mail.com', 'SOCIO', 1),
            ('45123789', 'Javier', 'Romero', '777777777', 'javier@mail.com', 'NO_SOCIO', NULL),
            ('27369123', 'Valeria', 'Suarez', '888888888', 'valeria@mail.com', 'SOCIO', 1),
            ('28145789', 'Martin', 'Fernandez', '999999999', 'martin@mail.com', 'NO_SOCIO', NULL),
            ('30214563', 'Sofia', 'Torres', '101010101', 'sofia@mail.com', 'SOCIO', 1)
            """
        )

        //   FACTURAS DE PRUEBA

        db.execSQL(
            """
            INSERT INTO factura
            (usuarioId, fecha_vencimiento, fecha_pago, importe)
            VALUES
            (1, 1700000000000, NULL, 5000),
            (2, strftime('%s','now') * 1000, NULL, 5000),
            (3, 1700000000000, NULL, 4500),
            (4, 1700000000000, 1750000000000, 5000),
            (5, 1700000000000, NULL, 6000),
            (6, 1700000000000, 1750000000000, 5000),
            (7, 1700000000000, NULL, 5500)
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS cliente")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS factura")
        onCreate(db)
    }

    fun getClienteDao(): ClienteDao = ClienteDao(writableDatabase)
    fun getUsuarioDao(): UsuarioDao = UsuarioDao(writableDatabase)
    fun getFacturaDao(): FacturaDao = FacturaDao(writableDatabase)
}
