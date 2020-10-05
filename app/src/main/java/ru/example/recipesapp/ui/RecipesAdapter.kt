package ru.example.recipesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recipe.view.*
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.model.Meal
import ru.example.recipesapp.databinding.ItemRecipeBinding


class RecipesAdapter(
    private val listener: (Meal) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.RecipeHolder>() {


    private val data = ArrayList<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder =
        RecipeHolder.create(parent)

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.btnViewRecipe.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int = data.size

    fun addItems(meals: List<Meal>) {
        data.addAll(meals)
        notifyDataSetChanged()
    }

    class RecipeHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            bindImage(meal.image!!)
            binding.mealName = meal.title
            binding.executePendingBindings()
        }

        private fun bindImage(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .into(binding.root.mealImage)
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