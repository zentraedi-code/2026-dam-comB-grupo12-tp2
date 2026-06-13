package com.dam.tp2dam.app.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "club.db", null, 3) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE cliente(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni TEXT NOT NULL,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                telefono TEXT NOT NULL,
                email TEXT NOT NULL,
                habilitado INTEGER NOT NULL DEFAULT 1,
                es_socio INTEGER NOT NULL DEFAULT 0,
                fecha_alta TEXT,
                tipo_cliente TEXT NOT NULL,
                apto_fisico INTEGER
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

        val valores = ContentValues().apply {
            put("usuario", "admin")
            put("clave", "123456")
        }
        db.insert("usuario", null, valores)

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

        db.execSQL(
            """
            INSERT INTO cliente
            (dni, nombre, apellido, telefono, email, habilitado, es_socio, fecha_alta, tipo_cliente, apto_fisico)
            VALUES
            ('25632187', 'Juan', 'Perez', '111111111', 'juan@mail.com', 1, 1, '2026-01-10', 'SOCIO', 1),
            ('36215798', 'Maria', 'Gomez', '222222222', 'maria@mail.com', 1, 1, '2026-02-15', 'SOCIO', 1),
            ('32516017', 'Pedro', 'Lopez', '333333333', 'pedro@mail.com', 1, 0, '2026-03-20', 'NO_SOCIO', NULL),
            ('06321894', 'Ana', 'Martinez', '444444444', 'ana@mail.com', 1, 1, '2026-01-12', 'SOCIO', 1),
            ('36987125', 'Carlos', 'Suarez', '555555555', 'carlos@mail.com', 1, 0, '2026-04-05', 'NO_SOCIO', NULL),
            ('42365178', 'Lucia', 'Diaz', '666666666', 'lucia@mail.com', 1, 1, '2026-05-22', 'SOCIO', 1),
            ('45123789', 'Javier', 'Romero', '777777777', 'javier@mail.com', 1, 0, '2026-03-11', 'NO_SOCIO', NULL),
            ('27369123', 'Valeria', 'Suarez', '888888888', 'valeria@mail.com', 1, 1, '2026-02-19', 'SOCIO', 1),
            ('28145789', 'Martin', 'Fernandez', '999999999', 'martin@mail.com', 1, 0, '2026-01-30', 'NO_SOCIO', NULL),
            ('30214563', 'Sofia', 'Torres', '101010101', 'sofia@mail.com', 1, 1, '2026-04-14', 'SOCIO', 1)
            """
        )

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
        db.execSQL("DROP TABLE IF EXISTS factura")
        db.execSQL("DROP TABLE IF EXISTS cliente")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        onCreate(db)
    }

    fun getClienteDao(): ClienteDao = ClienteDao(writableDatabase)
    fun getUsuarioDao(): UsuarioDao = UsuarioDao(writableDatabase)
    fun getFacturaDao(): FacturaDao = FacturaDao(writableDatabase)
}