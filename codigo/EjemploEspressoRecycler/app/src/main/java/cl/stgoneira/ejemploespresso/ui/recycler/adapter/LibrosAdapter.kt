package cl.stgoneira.ejemploespresso.ui.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.stgoneira.ejemploespresso.data.remote.Libro
import cl.stgoneira.ejemploespresso.databinding.ItemLibroBinding
import cl.stgoneira.ejemploespresso.ui.DetalleLibroActivity
import com.squareup.picasso.Picasso


const val LIBROID_MESSAGE = "cl.stgoneira.anchorbooks.LIBROID"

class LibrosAdapter(private val libros:List<Libro>) : RecyclerView.Adapter<LibrosAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemLibroBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLibroBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libro = libros[position]
        with(holder.binding) {
            tvTitulo.text = libro.titulo
            tvAutor.text = libro.autor
            Picasso.get().load(libro.imagen).into(imageView)
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(it.context, DetalleLibroActivity::class.java).apply {
                putExtra(LIBROID_MESSAGE, libro.id)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return libros.size
    }


}