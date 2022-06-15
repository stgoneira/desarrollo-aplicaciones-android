package cl.stgoneira.ejemplomvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.stgoneira.ejemplomvvm.databinding.LibroItemBinding
import cl.stgoneira.ejemplomvvm.model.api.LibroModel
import com.squareup.picasso.Picasso

class LibroAdapter(private val datos:List<LibroModel>): RecyclerView.Adapter<LibroAdapter.ViewHolder>() {

    class ViewHolder(val binding: LibroItemBinding):RecyclerView.ViewHolder(binding.root)

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
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}