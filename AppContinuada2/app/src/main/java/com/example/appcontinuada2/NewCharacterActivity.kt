package com.example.appcontinuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appcontinuada2.models.Personagem
import com.example.appcontinuada2.services.ApiPersonagensRequest
import kotlinx.android.synthetic.main.activity_new_character.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewCharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
    }

    fun criarNovoAtualizarPersonagem(componente:View) {
        if (et_nome.text.isBlank() || et_arma.text.isBlank() || et_idade.text.isBlank()) {
            Toast.makeText(this, "${getString(R.string.toast_campos)}", Toast.LENGTH_LONG).show()
        } else {
            var nome = et_nome.text.toString()
            var idade = et_idade.text.toString().toInt()
            var arma = et_arma.text.toString()
            var id: Int? = null
            if (et_id.text.isBlank()) {
                id = null
            } else {
                id = et_id.text.toString().toInt()
            }

            var orientacao: Boolean = false
            if (sw_lado.isChecked) {
                orientacao = true
            }
            var imagem: String? = ""
            if (et_imagem.text.isBlank()) {
                imagem = null
            } else {
                imagem = et_imagem.text.toString()
            }

            val personagem = Personagem(id, nome, orientacao, arma, idade, imagem)


            val retrofit = Retrofit.Builder()
                .baseUrl("https://5f86391ac8a16a0016e6ace8.mockapi.io/stwrs-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val requests = retrofit.create(ApiPersonagensRequest::class.java)
            if (id == null) {
                val criar = requests.createNewCharacter(personagem)
                criar.enqueue( object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@NewCharacterActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() != 404) {
                            Toast.makeText(
                                this@NewCharacterActivity,
                                "${getString(R.string.toast_personagem_criado)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this@NewCharacterActivity,
                                "${getString(R.string.toast_erro)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }else{
                val atualizar = requests.updateCharacter(id, personagem)
                atualizar.enqueue( object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@NewCharacterActivity, "${getString(R.string.toast_erro)} $t", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() != 404) {
                            Toast.makeText(
                                this@NewCharacterActivity,
                                "${getString(R.string.toast_personagem_atualizado)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this@NewCharacterActivity,
                                "${getString(R.string.toast_personagem_nf)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    fun voltarTelaPersonagem(componente:View){
        val telaPersonagem = Intent(this@NewCharacterActivity, MainActivity::class.java)
        startActivity(telaPersonagem)
    }
}