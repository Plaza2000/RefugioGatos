package com.example.refugiogatos



import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etRaza: EditText
    private lateinit var etEdad: EditText
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var rvGatos: RecyclerView
    private lateinit var gatosList: ArrayList<Gato>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombre = findViewById(R.id.etNombreGato)
        etRaza = findViewById(R.id.etRazaGato)
        etEdad = findViewById(R.id.etEdadGato)
        rvGatos = findViewById(R.id.rvGatos)
        dbHelper = DatabaseHelper(this)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val nombre = etNombre.text.toString()
            val raza = etRaza.text.toString()
            val edad = etEdad.text.toString().toIntOrNull() ?: 0

            if (nombre.isNotEmpty() && raza.isNotEmpty() && edad > 0) {
                dbHelper.addGato(Gato(nombre = nombre, raza = raza, edad = edad))
                loadGatos()
                clearFields()
            } else {
                Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnViewAll).setOnClickListener {
            loadGatos()
        }

        loadGatos()
    }

    private fun loadGatos() {
        gatosList = dbHelper.getAllGatos()
        rvGatos.adapter = GatosAdapter(gatosList, dbHelper) {
            loadGatos() // Actualiza la lista cuando hay cambios.
        }
        rvGatos.layoutManager = LinearLayoutManager(this)
    }

    private fun clearFields() {
        etNombre.text.clear()
        etRaza.text.clear()
        etEdad.text.clear()
    }
}
