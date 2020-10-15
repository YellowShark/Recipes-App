package ru.example.recipesapp.ui.search.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_filters.*
import ru.example.recipesapp.R
import ru.example.recipesapp.ui.BaseFragment
import ru.example.recipesapp.ui.search.main.FragmentFilters.Cuisines.*
import ru.example.recipesapp.ui.search.main.FragmentFilters.MealTypes.*
import ru.example.recipesapp.utils.underscoreToSpace

class FragmentFilters : BaseFragment(R.layout.fragment_filters), View.OnClickListener {

    private var cuisine = ""
    private var diet = ""
    private var type = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cuisine_btn.setOnClickListener(this)
        diet_btn.setOnClickListener(this)
        typeOfRecipe_btn.setOnClickListener(this)
        apply_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cuisine_btn -> {
                showCuisinesDialog()
            }
            R.id.diet_btn -> {
                showDietsDialog()
            }
            R.id.typeOfRecipe_btn -> {
                showTypeDialog()
            }
            R.id.apply_btn -> {
                val actionBack =
                    FragmentFiltersDirections.actionDestinationFiltersToDestinationSearch()
                actionBack.filterCuisine = cuisine
                actionBack.filterDiet = diet
                actionBack.filterType = type
                view?.let {
                    Navigation.findNavController(it).navigate(actionBack)
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun showCuisinesDialog() {
        AlertDialog.Builder(context)
            .setTitle("Choose a cuisine")
            .setItems(R.array.cuisines) { _, which ->
                cuisine = when (which) {
                    ALL.ordinal -> ""

                    AMERICAN.ordinal -> AMERICAN.name.toLowerCase()

                    BRITISH.ordinal -> BRITISH.name.toLowerCase()

                    CHINESE.ordinal -> CHINESE.name.toLowerCase()

                    EASTERN_EUROPE.ordinal -> EASTERN_EUROPE.name.underscoreToSpace().toLowerCase()

                    EUROPEAN.ordinal -> EUROPEAN.name.toLowerCase()

                    FRENCH.ordinal -> FRENCH.name.toLowerCase()

                    else -> ""
                }
            }
            .create()
            .show()
    }

    @SuppressLint("DefaultLocale")
    private fun showDietsDialog() {
        AlertDialog.Builder(context)
            .setTitle("Choose a diet")
            .setItems(R.array.diets) { _, which ->
                diet = when (which) {
                    Diets.ALL.ordinal -> ""
                    Diets.VEGETARIAN.ordinal -> Diets.VEGETARIAN.name.toLowerCase()
                    Diets.VEGAN.ordinal -> Diets.VEGAN.name.toLowerCase()
                    Diets.GLUTEN_FREE.ordinal -> Diets.GLUTEN_FREE.name.underscoreToSpace().toLowerCase()
                    Diets.KETOGENIC.ordinal -> Diets.KETOGENIC.name.toLowerCase()
                    else -> ""
                }
            }
            .create()
            .show()
    }

    @SuppressLint("DefaultLocale")
    private fun showTypeDialog() {
        AlertDialog.Builder(context)
            .setTitle("Choose a meal type")
            .setItems(R.array.meal_types) {_, which ->
                type = when(which) {
                    All.ordinal -> ""
                    MAIN_COURSE.ordinal -> MAIN_COURSE.name.underscoreToSpace().toLowerCase()
                    SIDE_DISH.ordinal -> SIDE_DISH.name.underscoreToSpace().toLowerCase()
                    DESSERT.ordinal -> DESSERT.name.toLowerCase()
                    APPETIZER.ordinal -> APPETIZER.name.toLowerCase()
                    SALAD.ordinal -> SALAD.name.toLowerCase()
                    BREAD.ordinal -> BREAD.name.toLowerCase()
                    BREAKFAST.ordinal -> BREAKFAST.name.toLowerCase()
                    SOUP.ordinal -> SOUP.name.toLowerCase()
                    SAUCE.ordinal -> SAUCE.name.toLowerCase()
                    SNACK.ordinal -> SNACK.name.toLowerCase()
                    DRINK.ordinal -> DRINK.name.toLowerCase()
                    else -> ""
                }
            }
            .create()
            .show()
    }

    enum class Cuisines {
        ALL,
        AMERICAN,
        BRITISH,
        CHINESE,
        EASTERN_EUROPE,
        EUROPEAN,
        FRENCH;
    }

    enum class Diets {
        ALL,
        VEGETARIAN,
        VEGAN,
        GLUTEN_FREE,
        KETOGENIC;
    }

    enum class MealTypes {
        All,
        MAIN_COURSE,
        SIDE_DISH,
        DESSERT,
        APPETIZER,
        SALAD,
        BREAD,
        BREAKFAST,
        SOUP,
        SAUCE,
        SNACK,
        DRINK;
    }
}