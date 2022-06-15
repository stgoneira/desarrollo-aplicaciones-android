package cl.aiep.android.anchorbooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.aiep.android.anchorbooks.R
import cl.aiep.android.anchorbooks.databinding.LibroItemBinding
import cl.aiep.android.anchorbooks.modelo.Libro
import com.squareup.picasso.Picasso

class LibroAdapter(private val datos:List<Libro>):RecyclerView.Adapter<LibroAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        //lateinit var binding: LibroItemBinding
        var titulo:TextView
        var imagen:ImageView

        init {
            titulo = view.findViewById(R.id.tvTitulo)
            imagen = view.findViewById(R.id.imageView)
            //binding = LibroItemBinding.inflate( LayoutInflater.from(view.context) )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from( parent.context).inflate(R.layout.libro_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libro = datos.get(position)
        holder.titulo.text = libro.titulo
        Picasso.get().load(libro.imagen).into(holder.imagen)
        /*
        holder.binding.tvTitulo.text = libro.titulo
        holder.binding.tvAutor.text = libro.autor
        holder.binding.tvLenguaje.text = libro.lenguaje
        holder.binding.tvPais.text = libro.pais
        Picasso.get().load(libro.imagen).into(holder.binding.imageView)
         */
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}