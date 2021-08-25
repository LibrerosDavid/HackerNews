package com.examen.hackernews.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var binding: ItemArticuloBinding
    private val time = DateTimeFormatter.ofLocalizedDate(
        FormatStyle.FULL
    )


    companion object{
        var hackerListener:HackerListener? = null
    }

    fun setListener(hackerListener:HackerListener){
        HackerNewsRecyclerViewAdapter.hackerListener = hackerListener
    }



    private fun deleteData(articulo: Articulo){
        if (this.articulos.count() >0){
            val index = this.articulos.indexOf(articulo)
            Log.e("data info borrado-->","index:${index} ---- info-:${articulo}")
            this.articulos.remove(articulo)

            notifyItemRemoved(index)
            notifyItemRangeChanged(index,itemCount)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemArticuloBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = ViewHolder(binding.root)

        binding.card.setOnClickListener {
            val position = holder.adapterPosition
            hackerListener?.onClick(position,0)
        }

        binding.borra.setOnClickListener {
            val position = holder.adapterPosition
            val model = this.articulos[position]
            deleteData(model)
            hackerListener?.onClick(position,1)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(
            title = this.articulos[position].story_title,
            autor = this.articulos[position].author,
            date = this.articulos[position].created_at,
            id = this.articulos[position].objectID
        )


    }

    override fun getItemCount(): Int {
        return articulos.size
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        @SuppressLint("SetTextI18n")
        fun bind(title: String?, autor: String, date: LocalDateTime, id:Long) {
            val fecha = LocalDateTime.parse(date.toString()).format(time)
            binding.title.text = title
            binding.autorAndTime.text = "$autor - $fecha"

            binding.idOb.text = "id-> $id"
        }
    }

    interface HackerListener{
        fun onClick(position: Int,tipo:Int)
    }
}