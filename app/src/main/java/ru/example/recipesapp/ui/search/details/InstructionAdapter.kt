package ru.example.recipesapp.ui.search.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_step.view.*
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.model.details.Instruction
import ru.example.recipesapp.data.network.model.details.Step
import ru.example.recipesapp.databinding.ItemStepBinding
import java.util.*

class InstructionAdapter : RecyclerView.Adapter<InstructionAdapter.StepHolder>() {

    private val data = ArrayList<Step>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StepHolder.create(parent)

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun setItems(instructions: List<Instruction>) {
        data.clear()
        try {
            data.addAll(instructions[0].steps)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
        notifyDataSetChanged()
    }

    class StepHolder(private val binding: ItemStepBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Step) {
            with(binding) {
                stepNum = "Step ${step.number}"

                if (step.ingredients.isNotEmpty()) {
                    val sb = StringBuilder()
                    for (i in step.ingredients) {
                        sb.append(i.name).append("\n")
                    }
                    ingredients = sb.toString()
                }
                else
                    root.titleIngredients.visibility = View.GONE

                instruction = step.step
                executePendingBindings()
            }
        }
        companion object {
            fun create(parent: ViewGroup): StepHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemStepBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_step, parent, false)
                return StepHolder(binding)
            }
        }
    }
}