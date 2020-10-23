package com.example.appcontinuada2.models

data class Personagem (
    val id:Integer,
    val nomeDoPersonagem: String,
    val orientacao:Boolean,
    val armaProficiente:String,
    val idade: Integer,
    val imagem:String
)