package com.example.developerbuddy.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developerbuddy.R
import com.example.developerbuddy.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


private const val TAG = "ListView"
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val stackListAdapter = StackListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[ListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel.refresh()


        stackList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackListAdapter
            observeViewModel()
        }

        refreshLayout.setOnRefreshListener {
            
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    fun observeViewModel() {
        viewModel.stackItems.observe(this, Observer { stack ->
            stack?.let {
                stackList.visibility = View.VISIBLE
                stackListAdapter.updateStackList(stack)
                Log.d(TAG, "Job count = ${stack.size}")
            }
        })

    }


}
