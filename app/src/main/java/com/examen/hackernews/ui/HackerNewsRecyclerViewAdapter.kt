package com.examen.hackernews.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.examen.hackernews.R
import com.examen.hackernews.databinding.ItemArticuloBinding
import com.examen.hackernews.model.Articulo
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.ArrayList

class HackerNewsRecyclerViewAdapter(private val context: Context,
                                    private var articulos:ArrayList<Articulo>
) :
    RecyclerView.Adapter<HackerNewsRecyclerViewAdapter.ViewHolder>() {
    private val time = DateTimeFormatter.ofLocalizedDate(
        FormatStyle.FULL
    )


    companion object{
        var hackerListener:HackerListener? = null
    }

    fun setListener(hackerListener:HackerListener){
        HackerNewsRecyclerViewAdapter.hackerListener = hackerListener
    }



     fun deleteData(articulo: Articulo){
        if (this.articulos.count() >0){
            val index = this.articulos.indexOf(articulo)
            Log.e("data info borrado-->","index:${index} ---- info-:${articulo}")
            this.articulos.remove(articulo)

            notifyItemRemoved(index)
            notifyItemRangeChanged(index,itemCount)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_articulo, parent, false)
        val holder = ViewHolder(view)

        holder.card.setOnClickListener {
            val position = holder.adapterPosition
            val model = this.articulos[position]

            hackerListener?.onClick(model,0)
        }

        holder.borra.setOnClickListener {
            val position = holder.adapterPosition
            val model = this.articulos[position]
            hackerListener?.onClick(model,1)
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fecha = LocalDateTime.parse(articulos[position].created_at.toString()).format(time)
        holder.title.text = articulos[position].story_title
        holder.autorAndTime.text = "${articulos[position].author} - $fecha"
        holder.idOb.text = "id-> ${articulos[position].objectID}"

    }

    override fun getItemCount(): Int {
        return articulos.size
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val card: CardView = item.findViewById(R.id.card)
        val autorAndTime: TextView = item.findViewById(R.id.autor_and_time)
        val idOb: TextView = item.findViewById(R.id.id_ob)
        val title: TextView = item.findViewById(R.id.title)
        val borra: Button = item.findViewById(R.id.borra)

    }

    interface HackerListener{
        fun onClick(articulo: Articulo,tipo:Int)
    }
}