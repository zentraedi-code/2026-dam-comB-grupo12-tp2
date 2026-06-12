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

        // Unico usuario admin/123456. No se hace abm de esta tabla
        val valores = ContentValues().apply {
            put("usuario", "admin")
            put("clave", "123456")
        }
        db.insert("usuario", null, valores)

        //Usuarios de prueba
        db.execSQL(
            """
        INSERT INTO cliente
        (dni, nombre, apellido, telefono, email, habilitado, es_socio, fecha_alta)
        VALUES
        ('25632187', 'Juan', 'Perez', '111111111', 'juan@mail.com', 1, 1, 1750000000000),
        ('36215798', 'Maria', 'Gomez', '222222222', 'maria@mail.com', 1, 1, 1750000000000),
        ('32516017', 'Pedro', 'Lopez', '333333333', 'pedro@mail.com', 1, 1, 1750000000000),
        ('06321894', 'Ana', 'Martinez', '444444444', 'ana@mail.com', 1, 1, 1750000000000),
        ('36987125', 'Carlos', 'Suarez', '555555555', 'carlos@mail.com', 1, 1, 1750000000000),
        ('42365178', 'Lucia', 'Diaz', '666666666', 'lucia@mail.com', 1, 1, 1750000000000),
        ('45123789', 'Javier', 'Romero', '777777777', 'javier@mail.com', 1, 1, 1750000000000),
        ('27369123', 'Valeria', 'Suarez', '888888888', 'valeria@mail.com', 1, 1, 1750000000000),
        ('28145789', 'Martin', 'Fernandez', '999999999', 'martin@mail.com', 1, 1, 1750000000000),
        ('30214563', 'Sofia', 'Torres', '101010101', 'sofia@mail.com', 1, 1, 1750000000000),

        ('22145789', 'Diego', 'Ruiz', '121212121', 'diego@mail.com', 1, 0, 1750000000000),
        ('29365147', 'Camila', 'Molina', '131313131', 'camila@mail.com', 1, 0, 1750000000000),
        ('33214569', 'Agustin', 'Castro', '141414141', 'agustin@mail.com', 1, 0, 1750000000000),
        ('46123789', 'Florencia', 'Vega', '151515151', 'florencia@mail.com', 1, 0, 1750000000000)
        """
        )

        db.execSQL(
            """
        INSERT INTO factura
        (usuarioId, fecha_vencimiento, fecha_pago, importe)
        VALUES
        (1, 1700000000000, NULL, 5000),
        (2, 1700000000000, 1750000000000, 5000),
        (3, 1700000000000, NULL, 4500),
        (4, 1700000000000, 1750000000000, 5000),
        (5, 1700000000000, NULL, 6000),
        (6, 1700000000000, 1750000000000, 5000),
        (7, 1700000000000, NULL, 5500)
        """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun getClienteDao(): ClienteDao {
        return ClienteDao(writableDatabase)
    }

    fun getUsuarioDao(): UsuarioDao {
        return UsuarioDao(writableDatabase)
    }

    fun getFacturaDao(): FacturaDao {
        return FacturaDao(writableDatabase)
    }
}
