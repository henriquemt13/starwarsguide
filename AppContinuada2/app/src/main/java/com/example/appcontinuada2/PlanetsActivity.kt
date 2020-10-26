package com.example.appcontinuada2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.appcontinuada2.models.Planeta
import com.example.appcontinuada2.services.ApiPlanetasRequest
import com.example.appcontinuada2.utils.CharacterCardsAdapter
import com.example.appcontinuada2.utils.PlanetCardsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_planets.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class PlanetsActivity : AppCompatActivity() {
    var posts = mutableListOf<Planeta>()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)


        getPlanetas()
    }

    fun getPlanetas(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/personagens/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiPlanetasRequest::class.java)

        val callPlanetas = requests.getAllPlanets()

        callPlanetas.enqueue(object : Callback<List<Planeta>> {
            override fun onFailure(call: Call<List<Planeta>>, t: Throwable) {
                Toast.makeText(this@PlanetsActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Planeta>>, response: Response<List<Planeta>>) {

                response.body()?.let {
                    criarCards(response.body())
                }

            }
        })
    }

    @SuppressLint("WrongConstant")
    fun criarCards(lista:List<Planeta>?){
        try{
            posts = lista as MutableList<Planeta>
            rv_planeta.layoutManager = LinearLayoutManager(this@PlanetsActivity, OrientationHelper.VERTICAL, false)
            rv_planeta.adapter = PlanetCardsAdapter(posts)
        }catch (e: Exception){
            Toast.makeText(this@PlanetsActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun deletarPlaneta(componete:View){
        val deletarPlaneta = Intent(this@PlanetsActivity, DeleteActivity::class.java)
        deletarPlaneta.putExtra("tipo", "planeta")
        startActivity(deletarPlaneta)
    }

    fun telaPersonagens(componente:View){
        val telaPersonagem = Intent(this@PlanetsActivity, MainActivity::class.java)
        startActivity(telaPersonagem)
    }

    fun criarAtualizar(componente:View){
        val criarAtualizar = Intent(this@PlanetsActivity, NewPlanetActivity::class.java)
        startActivity(criarAtualizar)
    }
}