package com.example.refugiogatos



import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
class EditGatoDialog(
    context: Context,
    private val gato: Gato,
    private val dbHelper: DatabaseHelper,
    private val onGatoUpdated: () -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_gato)

        val etEditNombre = findViewById<EditText>(R.id.etEditNombreGato)
        val etEditRaza = findViewById<EditText>(R.id.etEditRazaGato)
        val etEditEdad = findViewById<EditText>(R.id.etEditEdadGato)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        etEditNombre.setText(gato.nombre)
        etEditRaza.setText(gato.raza)
        etEditEdad.setText(gato.edad.toString())

        btnUpdate.setOnClickListener {
            val nombre = etEditNombre.text.toString()
            val raza = etEditRaza.text.toString()
            val edad = etEditEdad.text.toString().toIntOrNull() ?: 0

            if (nombre.isNotEmpty() && raza.isNotEmpty() && edad > 0) {
                gato.nombre = nombre
                gato.raza = raza
                gato.edad = edad
                dbHelper.updateGato(gato)
                onGatoUpdated()
                dismiss()
            } else {
                Toast.makeText(context, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
