package com.example.appcontinuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appcontinuada2.models.Planeta
import com.example.appcontinuada2.services.ApiPersonagensRequest
import com.example.appcontinuada2.services.ApiPlanetasRequest
import kotlinx.android.synthetic.main.activity_delete.*
import kotlinx.android.synthetic.main.activity_main.tv_titulo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeleteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        val tipo = intent.extras?.getString("tipo")
        if (tipo != null) {
            if (tipo.equals(getString(R.string.personagem_delete))){
                tv_titulo.text = "${getString(R.string.tv_titulo_delete)} ${getString(R.string.personagem_delete)}"
                tv_id.text = "${getString(R.string.tv_id_delete)} ${getString(R.string.personagem_delete)}"
            }else{
                tv_titulo.text = "${getString(R.string.tv_titulo_delete)} ${getString(R.string.planeta_delete)}"
                tv_id.text = "${getString(R.string.tv_id_delete)} ${getString(R.string.planeta_delete)}"
            }
        }


    }

    fun excluir(componente:View){
        val tipo = intent.extras?.getString("tipo")
        if (tipo.equals("personagem")){
            deletePersonagem()
        }else{
            deletePlaneta()
        }
    }

    fun deletePlaneta(){
        val id = et_id.text.toString().toInt()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiPlanetasRequest::class.java)

        val deletePlaneta = requests.deletePlanet(id)

        deletePlaneta.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DeleteActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    Toast.makeText(
                        this@DeleteActivity,
                        "${getString(R.string.toast_planeta_delete)}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DeleteActivity,
                        "${getString(R.string.toast_planeta_nf)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun deletePersonagem(){
        val id = et_id.text.toString().toInt()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/personagens/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiPersonagensRequest::class.java)

        val deletePersonagem = requests.deleteCharacter(id)

        deletePersonagem.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DeleteActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    Toast.makeText(
                        this@DeleteActivity,
                        "${getString(R.string.toast_personagem_delete)}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DeleteActivity,
                        "${getString(R.string.toast_personagem_nf)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun voltarTela(componente:View){
        val tipo = intent.extras?.getString("tipo")

        if (tipo.equals("planeta")){
            val tela = Intent(this@DeleteActivity, PlanetsActivity::class.java)
            startActivity(tela)
        }else{
            val tela = Intent(this@DeleteActivity, MainActivity::class.java)
            startActivity(tela)
        }
    }
}