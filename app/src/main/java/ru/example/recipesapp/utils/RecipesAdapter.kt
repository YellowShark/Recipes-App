package ru.example.recipesapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.example.recipesapp.R
import ru.example.recipesapp.data.model.Meal
import ru.example.recipesapp.databinding.ItemRecipeBinding


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipeHolder>() {

    private val data = ArrayList<Meal>()

    init {
        data.add((Meal(123, "Borsch")))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder =
        RecipeHolder.create(parent)

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun addItems(meals: List<Meal>) = data.addAll(meals)

    class RecipeHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.mealName = meal.title
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): RecipeHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemRecipeBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_recipe, parent, false)
                return RecipeHolder(binding)
            }
        }
    }
}