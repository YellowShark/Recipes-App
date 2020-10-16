package ru.example.recipesapp.ui.search.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_filters.*
import ru.example.recipesapp.R
import ru.example.recipesapp.ui.BaseFragment
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
                    Cuisines.ALL.ordinal -> ""
                    Cuisines.EASTERN_EUROPE.ordinal -> Cuisines.EASTERN_EUROPE.name.underscoreToSpace()
                        .toLowerCase()
                    else -> Cuisines.values()[which].name.toLowerCase()
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
                    Diets.GLUTEN_FREE.ordinal -> Diets.GLUTEN_FREE.name.underscoreToSpace()
                        .toLowerCase()
                    else -> Diets.values()[which].name.toLowerCase()
                }
            }
            .create()
            .show()
    }

    @SuppressLint("DefaultLocale")
    private fun showTypeDialog() {
        AlertDialog.Builder(context)
            .setTitle("Choose a meal type")
            .setItems(R.array.meal_types) { _, which ->
                type = when (which) {
                    MealTypes.All.ordinal -> ""
                    MealTypes.MAIN_COURSE.ordinal -> MealTypes.MAIN_COURSE.name.underscoreToSpace()
                        .toLowerCase()
                    MealTypes.SIDE_DISH.ordinal -> MealTypes.SIDE_DISH.name.underscoreToSpace()
                        .toLowerCase()
                    else -> MealTypes.values()[which].name.toLowerCase()
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