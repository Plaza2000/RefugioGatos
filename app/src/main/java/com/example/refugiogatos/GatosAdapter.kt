package com.example.refugiogatos



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class GatosAdapter(
    private val gatosList: ArrayList<Gato>,
    private val dbHelper: DatabaseHelper,
    private val onGatoUpdated: () -> Unit
) : RecyclerView.Adapter<GatosAdapter.GatoViewHolder>() {

    inner class GatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreGato)
        val tvRaza: TextView = itemView.findViewById(R.id.tvRazaGato)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdadGato)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditGato)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteGato)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GatoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gato_item, parent, false)
        return GatoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GatoViewHolder, position: Int) {
        val gato = gatosList[position]
        holder.tvNombre.text = gato.nombre
        holder.tvRaza.text = gato.raza
        holder.tvEdad.text = gato.edad.toString()

        holder.btnEdit.setOnClickListener {
            EditGatoDialog(holder.itemView.context, gato, dbHelper) {
                onGatoUpdated() // Llama a la función para recargar la lista en MainActivity.
            }.show()
        }

        holder.btnDelete.setOnClickListener {
            dbHelper.deleteGato(gato.id)
            gatosList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, gatosList.size)
        }
    }

    override fun getItemCount(): Int = gatosList.size
}
