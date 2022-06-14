package cl.stgoneira.ejemploretrofit.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.stgoneira.ejemploretrofit.R
import cl.stgoneira.ejemploretrofit.model.Foto
import com.squareup.picasso.Picasso

class FotoAdapter(private val datos:List<Foto>):RecyclerView.Adapter<FotoAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val textView:TextView
        val imageView:ImageView

        init {
            textView    = view.findViewById(R.id.textView)
            imageView   = view.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from( viewGroup.context ).inflate(R.layout.foto_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto = datos.get(position)
        holder.textView.text = foto.titulo
        Picasso.get().load(foto.miniatura).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}