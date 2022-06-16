package cl.aiep.android.anchorbooks.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.aiep.android.anchorbooks.DetalleActivity
import cl.aiep.android.anchorbooks.databinding.LibroItemBinding
import cl.aiep.android.anchorbooks.modelo.LibroModel
import com.squareup.picasso.Picasso

class LibroAdapter(private val datos:List<LibroModel>):RecyclerView.Adapter<LibroAdapter.ViewHolder>() {

    class ViewHolder(val binding:LibroItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LibroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libro = datos.get(position)
        with(holder.binding) {
            tvTitulo.text   = libro.titulo
            tvAutor.text    = "Autor: ${libro.autor}"
            tvLenguaje.text = "Lenguaje: ${libro.lenguaje}"
            tvPais.text     = "País: ${libro.pais}"
            Picasso.get().load(libro.imagen).into(imageView)
        }

        // onclick libro item
        holder.binding.root.setOnClickListener(View.OnClickListener {
            val intent = Intent(it.context, DetalleActivity::class.java)
            it.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}