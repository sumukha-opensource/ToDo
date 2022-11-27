package com.ksa.todo.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ksa.todo.R
import com.ksa.todo.data.models.Priority
import com.ksa.todo.data.models.ToDoData
import com.ksa.todo.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<
        ListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class ListViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: ToDoData) {
            binding.titleText.text = toDoData.title
            binding.descriptionTextview.text = toDoData.description
            when (toDoData.priority) {
                Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val rowBinding =
            RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(
            rowBinding
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val toDoItem = dataList[position]
        holder.bind(toDoItem)
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>){
        dataList = toDoData
        notifyDataSetChanged()
    }
}