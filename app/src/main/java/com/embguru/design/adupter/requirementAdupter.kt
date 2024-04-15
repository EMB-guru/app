package com.embguru.design.adupter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.R
import com.embguru.design.model.requirementViewModel

class requirementAdupter(private val mList: List<requirementViewModel>) : RecyclerView.Adapter<requirementAdupter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.requirement_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.Id.text=ItemsViewModel.Id
        holder.name.text=ItemsViewModel.name
        holder.date.text=ItemsViewModel.date
        holder.status.text=ItemsViewModel.status
        holder.line.text=ItemsViewModel.line

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val Id: TextView = itemView.findViewById(R.id.Id)
        val name: TextView = itemView.findViewById(R.id.name)
        val date: TextView = itemView.findViewById(R.id.date)
        val status: TextView = itemView.findViewById(R.id.status)
        val line: TextView = itemView.findViewById(R.id.line)
    }
}