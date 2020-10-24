package ru.example.recipesapp.data.network.details.response

data class Instruction(
    val name: String,
    val steps: List<Step>
)