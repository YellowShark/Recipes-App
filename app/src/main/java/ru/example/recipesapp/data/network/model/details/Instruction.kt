package ru.example.recipesapp.data.network.model.details

data class Instruction(
    val name: String,
    val steps: List<Step>
)