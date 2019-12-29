package com.example.developerbuddy.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.developerbuddy.R
import com.example.developerbuddy.model.Stack
import com.example.developerbuddy.utils.getProgressDrawable
import com.example.developerbuddy.utils.loadImage
import com.example.developerbuddy.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var stackUuid = 0

    private lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.unHideFAB(true)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[ListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        arguments?.let {
            stackUuid = DetailFragmentArgs.fromBundle(it).uuid

        }

        val stack = viewModel.getDetailPic(stackUuid)
        if (stack != null) {
            setDetails(stack)
        }
    }

    private fun setDetails(stack: Stack) {
        detail_image_view.loadImage(
            stack.profile_image,
            getProgressDrawable(view!!.context)
        )

        val string = "Reputation = ${stack.reputation}\n" +
                "Rank = ${stack.rank}\n" +
                "User type = ${stack.user_type}\n"

        detail_textView.text = string

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement Listener")

        }
    }

    override fun onDetach() {
        super.onDetach()
        listener.unHideFAB(false)
    }

    internal interface Listener {
        fun unHideFAB(b: Boolean)
    }


}
