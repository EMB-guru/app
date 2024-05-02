package com.embguru.design.adupter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.R
import com.embguru.design.model.viewAllCategoryModel

class viewAllCategoryAdupter(private val context: Context,private val mList: List<viewAllCategoryModel>) : RecyclerView.Adapter<viewAllCategoryAdupter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_all_category_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.folder_recyclerview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val sortedItem=ItemsViewModel.list?.sortedByDescending { it.time }
        val adapter = sortedItem?.let { shortFolderAdupter(context, it) }
        holder.folder_recyclerview?.adapter = adapter
        holder.name.text=ItemsViewModel.name
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val folder_recyclerview: RecyclerView = itemView.findViewById(R.id.folder_recyclerview)

    }
}