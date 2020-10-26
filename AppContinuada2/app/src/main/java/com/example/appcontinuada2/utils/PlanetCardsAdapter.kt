package com.example.appcontinuada2.utils

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontinuada2.R
import com.example.appcontinuada2.models.Personagem
import com.example.appcontinuada2.models.Planeta
import com.squareup.picasso.Picasso

class PlanetCardsAdapter(val posts: List<Planeta>): RecyclerView.Adapter<PlanetCardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.planets_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts != null) {
            if (posts[position].imagem != null || posts[position].imagem != "") {
                Picasso.get().load(posts[position].imagem).into(holder.imagem)
            }else{
                Picasso.get()
                    .load("https://cdnb.artstation.com/p/assets/images/images/009/667/663/large/jose-mikhail-planet-coruscant-jm.jpg?1520252285")
                    .into(holder.imagem)
            }
              holder.nome.text =  posts[position].nome
              holder.nome.setTypeface(null, Typeface.BOLD)
              holder.populacao.text =  "População: " + posts[position].populacao
              holder.bioma.text = "Bioma predominante: " + posts[position].biomaPredominante

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagem: ImageView = itemView.findViewById(R.id.imagem_planeta)
        val nome: TextView = itemView.findViewById(R.id.nome_planeta)
        val populacao: TextView = itemView.findViewById(R.id.populacao)
        val bioma: TextView = itemView.findViewById(R.id.bioma)


    }
}