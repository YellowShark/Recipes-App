package ru.example.recipesapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.Status
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.databinding.FragmentRecipeDetailsBinding

class FragmentRecipeDetails : Fragment() {

    private val viewModel: RecipeDetailsViewModel by viewModel()
    private lateinit var instructionAdapter: InstructionAdapter
    private lateinit var binding: FragmentRecipeDetailsBinding

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
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        initData()
    }

    private fun initUi() {
        instructionAdapter = InstructionAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = instructionAdapter
    }

    private fun initData() {
        arguments?.let {
            val safeArgs = FragmentRecipeDetailsArgs.fromBundle(it)
            viewModel.requestDetails(safeArgs.recipeId)
        }
        viewModel.liveData.observe(this, {event ->
            when (event.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onSuccess(event.data)
                Status.ERROR -> onError()
            }
        })
    }

    private fun onSuccess(data: ResponseDetails?) {
        details_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        instructionAdapter.setItems(data!!.analyzedInstructions[0].steps)
        viewModel.recipeName.value = data.title
        viewModel.recipeImage.value = data.image
    }

    private fun onError() {
        Toast.makeText(activity, "Error!!!", Toast.LENGTH_SHORT).show()
    }

    private fun onLoading() {
        details_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewmodel = null
        binding.executePendingBindings()
    }
}