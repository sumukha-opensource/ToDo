package com.ksa.todo.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ksa.todo.R
import com.ksa.todo.data.models.ToDoData
import com.ksa.todo.data.viewmodel.ToDoViewModel
import com.ksa.todo.databinding.FragmentAddBinding
import com.ksa.todo.fragments.SharedViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        binding.prioritySpinner.onItemSelectedListener = mSharedViewModel.itemSelectedListener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEditText.text.toString()
        val priority = binding.prioritySpinner.selectedItem.toString()
        val mDescription = binding.descriptionEdittext.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val newData =
                ToDoData(0, mTitle, mSharedViewModel.parsePriority(priority), mDescription)
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!!..", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill all the fields!!..", Toast.LENGTH_SHORT)
                .show()
        }

    }
}