package com.dam.tp2dam

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dam.tp2dam.databinding.ActivityMainBinding
import com.dam.tp2dam.app.utils.ThemeManager

import com.dam.tp2dam.app.ui.auth.login.AdminLoginActivity
import com.dam.tp2dam.app.ui.clientes.ClienteActivity   // ← NUEVO IMPORT

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actualizarUI()
        setupClickListeners()
    }

    private fun actualizarUI() {
        val isDark = (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        binding.ivLogo.setImageResource(
            if (isDark) R.drawable.natacion_white else R.drawable.natacion
        )

        binding.btnToggleTheme.text = if (isDark) "☀" else "🌙"
    }

    private fun setupClickListeners() {

        // Cambiar tema
        binding.btnToggleTheme.setOnClickListener {
            ThemeManager.toggleTheme(this)
            val isDark = ThemeManager.isDarkMode(this)
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Acceso admin
        binding.btnAcceder.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }

        // NUEVO: botón para gestionar clientes
        binding.btnRegistrarCliente.setOnClickListener {
            startActivity(Intent(this, ClienteActivity::class.java))
        }

        // Redes sociales
        binding.btnInstagram.setOnClickListener { openUrl("https://www.instagram.com") }
        binding.btnLinkedin.setOnClickListener  { openUrl("https://www.linkedin.com") }
        binding.btnYoutube.setOnClickListener   { openUrl("https://www.youtube.com") }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu) = false

    private fun openUrl(url: String) {
        runCatching { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
    }
}
