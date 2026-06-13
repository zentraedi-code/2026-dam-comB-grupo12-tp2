package com.dam.tp2dam.app.ui.clientes

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.app.domain.Cliente

class ClientesAdapter(
    private val lista: MutableList<Cliente>,
    private val helper: SQLiteHelper
) : RecyclerView.Adapter<ClientesAdapter.ViewHolder>() {

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val tvDetalleCliente = vista.findViewById<TextView>(R.id.tvDetalleCliente)
        val btnCarnet = vista.findViewById<ImageButton>(R.id.btnCarnet)
        val btnCobrar = vista.findViewById<ImageButton>(R.id.btnCobrar)
        val btnEditar = vista.findViewById<ImageButton>(R.id.btnEditar)
        val btnEliminar = vista.findViewById<ImageButton>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = lista[position]

        holder.tvDetalleCliente.text =
            "${cliente.dni} - ${cliente.nombre} (${cliente.tipoCliente})"

        holder.btnEliminar.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar Cliente")
                .setMessage("¿Eliminar ${cliente.nombre}?")
                .setPositiveButton("Eliminar") { _, _ ->
                    val db = helper.writableDatabase
                    db.delete("cliente", "dni = ?", arrayOf(cliente.dni))
                    cargarClientes()
                    Toast.makeText(holder.itemView.context, "Cliente eliminado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        holder.btnCarnet.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "TODO imprimir carnet de ${cliente.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.btnCobrar.setOnClickListener {
            mostrarDialogoPago(holder.itemView.context, cliente)
        }

        holder.btnEditar.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "TODO editar cliente ${cliente.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun mostrarDialogoPago(context: Context, cliente: Cliente) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_pago)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tvCliente = dialog.findViewById<TextView>(R.id.tvClientePago)
        val etMonto = dialog.findViewById<EditText>(R.id.etMonto)
        val etConcepto = dialog.findViewById<EditText>(R.id.etConcepto)
        val btnConfirmar = dialog.findViewById<Button>(R.id.btnConfirmarPago)
        val btnCancelar = dialog.findViewById<Button>(R.id.btnCancelarPago)

        tvCliente.text = "Cliente: ${cliente.nombre} ${cliente.apellido} (DNI: ${cliente.dni})"

        btnConfirmar.setOnClickListener {
            val monto = etMonto.text.toString().trim()
            val concepto = etConcepto.text.toString().trim()

            if (monto.isEmpty()) {
                etMonto.error = "Ingrese el monto"
                return@setOnClickListener
            }

            val montoDouble = monto.toDoubleOrNull()
            if (montoDouble == null || montoDouble <= 0) {
                etMonto.error = "Monto inválido"
                return@setOnClickListener
            }

            if (concepto.isEmpty()) {
                etConcepto.error = "Ingrese el concepto"
                return@setOnClickListener
            }

            registrarPago(context, cliente, montoDouble)
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun registrarPago(context: Context, cliente: Cliente, monto: Double) {
        try {
            val db = helper.writableDatabase

            val cursor = db.rawQuery(
                "SELECT id FROM cliente WHERE dni = ?",
                arrayOf(cliente.dni)
            )

            if (!cursor.moveToFirst()) {
                Toast.makeText(context, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                cursor.close()
                return
            }

            val clienteId = cursor.getInt(0)
            cursor.close()

            val fechaActual = System.currentTimeMillis()

            val valores = ContentValues().apply {
                put("usuarioId", clienteId)
                put("fecha_vencimiento", fechaActual)
                put("fecha_pago", fechaActual)
                put("importe", monto)
            }

            db.insert("factura", null, valores)

            Toast.makeText(
                context,
                "Pago registrado para ${cliente.nombre}",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun cargarClientes() {
        val clienteDao = helper.getClienteDao()
        val clientesDB = clienteDao.obtenerTodos()
        lista.clear()
        lista.addAll(clientesDB)
        notifyDataSetChanged()
    }
}
