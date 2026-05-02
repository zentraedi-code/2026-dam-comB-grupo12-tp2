package com.dam.tp2dam.app.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.dam.tp2dam.R
import com.dam.tp2dam.app.ui.nosocio.RegisterNoSocioActivity
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity
import com.dam.tp2dam.app.ui.socio.RegisterSocioActivity
import com.dam.tp2dam.app.utils.ThemeManager

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnAceptar)
            .setOnClickListener {
                startActivity(Intent(this, PanelControlActivity::class.java));
            }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancelar)
            .setOnClickListener {
                finish();
            }

    }

}