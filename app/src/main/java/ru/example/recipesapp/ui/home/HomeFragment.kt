package ru.example.recipesapp.ui.home

import android.os.Bundle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import ru.example.recipesapp.R
import ru.example.recipesapp.ui.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnGoSearch.setOnClickListener {
            val actionSearch = HomeFragmentDirections.actionToSearch()
            view?.let { Navigation.findNavController(it).navigate(actionSearch) }
        }
    }
}