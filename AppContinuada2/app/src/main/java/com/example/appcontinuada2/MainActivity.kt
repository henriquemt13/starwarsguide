package com.example.appcontinuada2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.appcontinuada2.models.Personagem
import com.example.appcontinuada2.services.ApiPersonagensRequest
import com.example.appcontinuada2.utils.CharacterCardsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    var posts = mutableListOf<Personagem>()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_personagens.layoutManager = LinearLayoutManager(this@MainActivity, OrientationHelper.VERTICAL, false)
        getPersonagens()
    }

    fun getPersonagens(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/personagens/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiPersonagensRequest::class.java)

        val callPersonagens = requests.getAllCharacters()

        callPersonagens.enqueue(object : Callback<List<Personagem>> {
            override fun onFailure(call: Call<List<Personagem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Personagem>>, response: Response<List<Personagem>>) {

                response.body()?.let {
                    criarCards(response.body())
                }

            }
        })
    }

    @SuppressLint("WrongConstant")
    fun criarCards(lista:List<Personagem>?){
        try{
            posts = lista as MutableList<Personagem>
            rv_personagens.layoutManager = LinearLayoutManager(this@MainActivity, OrientationHelper.VERTICAL, false)
            rv_personagens.adapter = CharacterCardsAdapter(posts)
        }catch (e: Exception){
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun telaPlanetas(componente:View){
        val telaPlanetas = Intent(this@MainActivity, PlanetsActivity::class.java)
        startActivity(telaPlanetas)
    }

    fun deletarPersonagem(componente:View){
        val deletarPersonagem = Intent(this@MainActivity, DeleteActivity::class.java)
        deletarPersonagem.putExtra("tipo", "personagem")
        startActivity(deletarPersonagem)
    }
    fun criarAtualizarPersonagem(componente:View){
        val criarAtualizar = Intent(this@MainActivity, NewCharacterActivity::class.java)
        startActivity(criarAtualizar)
    }
}