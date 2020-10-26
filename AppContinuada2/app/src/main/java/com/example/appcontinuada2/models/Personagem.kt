package com.example.appcontinuada2.models

data class Personagem(
    val id:Int?,
    val nomeDoPersonagem: String,
    val orientacao:Boolean,
    val armaProficiente:String,
    val idade: Int,
    val imagem:String?
)