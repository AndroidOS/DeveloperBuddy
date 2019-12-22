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
import com.casa.azul.dogs.viewmodel.ListViewModel
import com.example.developerbuddy.R
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

//        val action = ListFragmentDirections.actionListFragmentToDetailFragment(20)
//        Navigation.findNavController(view)
//            .navigate(action)

        stackList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackListAdapter
            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.soItems.observe(this, Observer { stack ->
            stack?.let {
                stackList.visibility = View.VISIBLE
                stackListAdapter.updateStackList(stack)
                Log.d(TAG, "Job count = ${stack.size}")
            }
        })

    }


}
