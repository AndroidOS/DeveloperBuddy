package com.example.developerbuddy.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.developerbuddy.R
import com.example.developerbuddy.model.Stack
import com.example.developerbuddy.utils.getProgressDrawable
import com.example.developerbuddy.utils.loadImage
import kotlinx.android.synthetic.main.stack_item.view.*


private const val TAG = "StackListAdapter"
class StackListAdapter(val stackList: ArrayList<Stack>) :
    RecyclerView.Adapter<StackListAdapter.StackViewHolder>() {

    fun updateStackList(newGitJobsList: List<Stack>) {
        stackList.clear()
        stackList.addAll(newGitJobsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(
            R.layout.stack_item
            , parent, false
        )
        return StackViewHolder(view)
    }

    override fun getItemCount() = stackList.size

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        Log.d(TAG, "${stackList[position].profile_image}")
        holder.view.txt_title.text = stackList[position].display_name
        holder.view.txt_accept_rate.text = stackList[position].rank
        holder.view.stackView.loadImage(
            stackList[position].profile_image,
            getProgressDrawable(holder.view.stackView.context)
        )

        val uuid = position
        holder.view.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(uuid))
        }

    }

    class StackViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}