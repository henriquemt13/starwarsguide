package com.example.appcontinuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appcontinuada2.models.Planeta
import com.example.appcontinuada2.services.ApiPlanetasRequest
import kotlinx.android.synthetic.main.activity_new_planet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewPlanetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_planet)
    }


    fun criarNovoAtualizarPlaneta(componente: View) {
        if (et_nome.text.isBlank() || et_bioma.text.isBlank() || et_populacao.text.isBlank()) {
            Toast.makeText(this, "${getString(R.string.toast_campos)}", Toast.LENGTH_LONG).show()
        } else {
            var nome = et_nome.text.toString()
            var populacao = et_populacao.text.toString().toInt()
            var bioma = et_bioma.text.toString()
            var id: Int? = null
            if (et_id.text.isBlank()) {
                id = null
            } else {
                id = et_id.text.toString().toInt()
            }

            var imagem: String? = ""
            if (et_imagem.text.isBlank()) {
                imagem = null
            } else {
                imagem = et_imagem.text.toString()
            }

            val planeta = Planeta(id, nome, bioma, populacao, imagem)


            val retrofit = Retrofit.Builder()
                .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val requests = retrofit.create(ApiPlanetasRequest::class.java)
            if (id == null) {
                val criar = requests.createNewPlanet(planeta)
                criar.enqueue( object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@NewPlanetActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() != 404) {
                            Toast.makeText(
                                this@NewPlanetActivity,
                                "${getString(R.string.toast_planeta_criado)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this@NewPlanetActivity,
                                "${getString(R.string.toast_erro)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }else{
                val atualizar = requests.updatePlanet(id, planeta)
                atualizar.enqueue( object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@NewPlanetActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() != 404) {
                            Toast.makeText(
                                this@NewPlanetActivity,
                                "${getString(R.string.toast_planeta_atualizado)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this@NewPlanetActivity,
                                "${getString(R.string.toast_planeta_nf)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    fun voltarTelaPlaneta(componente: View){
        val telaPlaneta = Intent(this@NewPlanetActivity, PlanetsActivity::class.java)
        startActivity(telaPlaneta)
    }
}