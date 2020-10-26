package com.example.appcontinuada2.services

import com.example.appcontinuada2.models.Planeta
import retrofit2.Call
import retrofit2.http.*

interface ApiPlanetasRequest {
    @GET("/planeta")
    fun getAllPlanets(): Call<List<Planeta>>

    @POST("/planeta")
    fun createNewPlanet(@Body planeta: Planeta): Call<Void>

    @PUT("/planeta/{id}")
    fun updatePlanet(@Path("id") id: Int,@Body planeta: Planeta): Call<Void>

    @DELETE("/planeta/{id}")
    fun deletePlanet(@Path("id") id: Int): Call<Void>
}