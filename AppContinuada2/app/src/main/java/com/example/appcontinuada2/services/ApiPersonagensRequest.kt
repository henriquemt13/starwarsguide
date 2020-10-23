package com.example.appcontinuada2.services

import com.example.appcontinuada2.models.Personagem
import retrofit2.Call
import retrofit2.http.*

interface ApiPersonagensRequest {
    @GET("/personagem")
    fun getAllCharacters(): Call<List<Personagem>>

    @POST("/personagem")
    fun createNewCharacter(@Body personagem: Personagem):Call<Personagem>
}