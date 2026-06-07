package com.dam.tp2dam.app.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity
import com.google.android.material.textfield.TextInputEditText

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        helper = SQLiteHelper(this)

        configurarLogin()
        configurarCancelar()
    }

    private fun configurarLogin() {
        val etUsuario = findViewById<TextInputEditText>(R.id.etUsuario)
        val etClave = findViewById<TextInputEditText>(R.id.etClave)

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnAceptar)
            .setOnClickListener {
                val usuario = etUsuario.text.toString()
                val clave = etClave.text.toString()

                val usuarioDao = helper.getUsuarioDao()

                if (usuarioDao.validarLogin(usuario, clave)) {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PanelControlActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o clave incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun configurarCancelar() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancelar)
            .setOnClickListener {
                finish()
            }
    }

}
