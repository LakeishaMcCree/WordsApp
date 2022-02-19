package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding


/**
 * Entry fragment for the app. Displays a [RecyclerView] of letter.
 */
class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null

    //"get only" property, only valid between onCreateView and
    //onDestroyView
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    //keeps track  of which LayoutManager is in use for the [RecyclerView
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Retrieve and inflate the layout for this fragment
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        //Sets the LayoutManager of the recyclerview
        //On the first run of the app, it will be LinearLayoutManager
        chooseLayout()
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    /**
     * Sets the layoutManager for the [RecyclerView] based on the desired orientation of the list.
     *
     * Notice that because the enclosing class has changes from Activity to a Fragment,
     * the signature of the LayoutManagers has to slightly change.
     */
    private fun chooseLayout() {
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter()
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter()
            }
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    /**
     * Determines how to handle interactions with the selected [MenuItem]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }
            //Otherwise, do nothing and use the core event handling

            //when clauses require that all possible paths be accounted for explicitly,
            //for instance both the true and false cases if the value is a Boolean,
            //or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }
}