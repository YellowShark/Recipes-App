package ru.example.recipesapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.example.recipesapp.BR
import ru.example.recipesapp.R
import ru.example.recipesapp.data.model.Meal
import ru.example.recipesapp.databinding.ItemRecipeBinding
import ru.example.recipesapp.ui.RecipesViewModel

class Adapter : RecyclerView.Adapter<Adapter.RecipeHolder>() {

    private val viewModel = RecipesViewModel()

    private val data : List<Meal>
        get() = viewModel.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder
            = RecipeHolder.create(parent)

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    override fun getItemCount(): Int = data.size

    class RecipeHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: RecipesViewModel, position: Int) {
            binding.meal = viewModel.data[position].strMeal
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): RecipeHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding : ItemRecipeBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_recipe, parent, false)
                return RecipeHolder(binding)
            }
        }
    }
}