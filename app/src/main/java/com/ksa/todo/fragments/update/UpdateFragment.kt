package com.ksa.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ksa.todo.R
import com.ksa.todo.data.models.ToDoData
import com.ksa.todo.data.viewmodel.ToDoViewModel
import com.ksa.todo.databinding.FragmentUpdateBinding
import com.ksa.todo.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update, container,
            false
        )
        setHasOptionsMenu(true)

        binding.currenttitleEditText.setText(args.currentItem.title)
        binding.currentdescriptionEdittext.setText(args.currentItem.description)
        binding.currentprioritySpinner.setSelection(
            mSharedViewModel.parsePriorityToInt(args.currentItem.priority)
        )
        binding.currentprioritySpinner.onItemSelectedListener =
            mSharedViewModel.itemSelectedListener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_update -> updateItem()
            R.id.menu_delete -> deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){
            _,_ ->
            toDoViewModel.deleteData(args.currentItem)
            Toast.makeText(requireContext(),"Successfully Removed : ${args.currentItem.title}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){
            _,_ ->{}
        }
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}' ?")
        builder.create().show()
    }

    private fun updateItem() {
        val updatedTitle = binding.currenttitleEditText.text.toString()
        val descriptionText = binding.currentdescriptionEdittext.text.toString()
        val getPriority = binding.currentprioritySpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(updatedTitle, descriptionText)
        if (validation) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                updatedTitle,
                mSharedViewModel.parsePriority(getPriority),
                descriptionText
            )
            toDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Updated Successfully!..", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all the fields!..", Toast.LENGTH_SHORT)
                .show()
        }
    }
}