package com.example.developerbuddy.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.developerbuddy.R
import com.example.developerbuddy.model.Item
import kotlinx.android.synthetic.main.stack_item.view.*


class StackListAdapter(val stackList: ArrayList<Item>) :
    RecyclerView.Adapter<StackListAdapter.StackViewHolder>() {

    fun updateStackList(newGitJobsList: List<Item>) {
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
        holder.view.txt_title.text = stackList[position].user?.display_name

        val uuid = position
        holder.view.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(uuid))
        }

    }

    class StackViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}