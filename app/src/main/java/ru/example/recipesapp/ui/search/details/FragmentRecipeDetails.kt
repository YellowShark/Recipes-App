package ru.example.recipesapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.details.response.Details
import ru.example.recipesapp.databinding.FragmentRecipeDetailsBinding
import ru.example.recipesapp.ui.BaseFragment
import ru.example.recipesapp.utils.Event
import ru.example.recipesapp.utils.Status

class FragmentRecipeDetails : BaseFragment(R.layout.fragment_recipe_details) {

    private val viewModel: RecipeDetailsViewModel by viewModel()
    private val instructionAdapter by lazy { InstructionAdapter() }
    private lateinit var binding: FragmentRecipeDetailsBinding
    private val eventObserver = Observer<Event<Details>> { event ->
        when (event.status) {
            Status.LOADING -> onLoading()
            Status.SUCCESS -> onSuccess(event.data!!)
            Status.ERROR -> onError()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_details,
            container,
            false
        )

        binding.lifecycleOwner = this@FragmentRecipeDetails
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        initData()
    }

    private fun initUi() {
        recipesList_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = instructionAdapter
        }
    }

    private fun initData() {
        arguments?.let {
            val safeArgs = FragmentRecipeDetailsArgs.fromBundle(it)
            viewModel.requestDetails(safeArgs.recipeId)
        }
        viewModel.eventLive.observe(viewLifecycleOwner, eventObserver)
    }

    private fun onSuccess(data: Details) {
        details_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        instructionAdapter.setItems(data.analyzedInstructions)
        binding.apply {
            recipeName = data.title
            recipeImage = data.image
        }
    }

    private fun onError() {
        Toast.makeText(activity, "Error!!!", Toast.LENGTH_SHORT).show()
    }

    private fun onLoading() {
        details_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }
}