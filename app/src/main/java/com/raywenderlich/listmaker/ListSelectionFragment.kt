package com.raywenderlich.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListSelectionFragment : Fragment(),
        ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView

    private var listener: OnListItemFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException("$context must implement OnListItemFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lists = listDataManager.readLists()
        view?.let {
            listsRecyclerView = it.findViewById(R.id.lists_recyclerview)
            listsRecyclerView.layoutManager =
                LinearLayoutManager(activity)
            listsRecyclerView.adapter =
                ListSelectionRecyclerViewAdapter(lists, this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_selection,
                container, false)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun addList(list : TaskList) {
        listDataManager.saveList(list)
        val recyclerAdapter = listsRecyclerView.adapter as
                ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    interface OnListItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }

    companion object {
        fun newInstance(): ListSelectionFragment {
            return ListSelectionFragment()
        }
    }
}