package ru.example.recipesapp.ui.search.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recipe.view.*
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.model.search.Meal
import ru.example.recipesapp.databinding.ItemRecipeBinding


class RecipesAdapter(private val listener: (Meal) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_ITEM_TYPE = 0
    private val VIEW_LOADING_TYPE = 1
    private val data = ArrayList<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM_TYPE)
            ViewHolder.create(parent)
        else {
            Log.d("TAG", "onCreateViewHolder: hello")
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = data[position]
            holder.apply {
                bind(item as Meal)
                itemView.btnViewRecipe.setOnClickListener { listener(item as Meal) }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return if (data[position] is Meal) VIEW_ITEM_TYPE else VIEW_LOADING_TYPE
    }

    fun setItems(meals: List<Meal>) {
        data.clear()
        data.addAll(meals)
        notifyDataSetChanged()
    }

    fun addLoader() {
        data.add(Loader())
        notifyItemInserted(itemCount - 1)
    }

    fun removeLoader() {
        data.remove(Loader())
        notifyItemRemoved(itemCount - 1)
    }

    fun removeAll() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            bindImage(meal.image)
            with(binding) {
                mealName = meal.title
                executePendingBindings()
            }
        }

        private fun bindImage(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .into(binding.root.mealImage)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemRecipeBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_recipe, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    data class Loader(val data: String = "load")
    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}