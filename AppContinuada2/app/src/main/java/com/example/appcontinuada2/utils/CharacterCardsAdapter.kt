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
import com.squareup.picasso.Picasso

class CharacterCardsAdapter(val posts: List<Personagem>): RecyclerView.Adapter<CharacterCardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts != null) {
            if (posts[position].imagem != null || posts[position].imagem != "") {
                Picasso.get().load(posts[position].imagem).into(holder.imagem)
            }else{
                Picasso.get()
                    .load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTLclR1kT90l4rtEki2_UhvgmmQd-0vpEgirw&usqp=CAU")
                    .into(holder.imagem)
            }
            holder.nome.text =  posts[position].nomeDoPersonagem
            holder.nome.setTypeface(null, Typeface.BOLD)
            holder.idade.text =  "Idade: " + posts[position].idade.toString()
            holder.arma.text = "Arma favorita: " + posts[position].armaProficiente.toString()
            if (posts[position].orientacao) {
                holder.orientacao.text = "Lado Claro"
            }else{
                holder.orientacao.text = "Lado Negro"
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imagem: ImageView = itemView.findViewById(R.id.imagem)
        val nome: TextView = itemView.findViewById(R.id.nome)
        val idade: TextView = itemView.findViewById(R.id.idade)
        val orientacao: TextView = itemView.findViewById(R.id.orientacao)
        val arma: TextView = itemView.findViewById(R.id.arma)

    }



}