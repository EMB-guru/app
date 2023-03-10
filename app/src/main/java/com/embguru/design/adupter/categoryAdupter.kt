package com.embguru.design.adupter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.CategoryPage
import com.embguru.design.HomePage
import com.embguru.design.R
import com.embguru.design.model.ItemsViewModel
import com.squareup.picasso.Picasso

class categoryAdupter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<categoryAdupter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Picasso.get().load(ItemsViewModel.image).centerCrop().fit().into(holder.imageView)

        holder.imageView.setOnClickListener {v->
            val changePage = Intent(v.context, CategoryPage::class.java)
            changePage.putExtra("categoryName", ItemsViewModel.text)
            v.context.startActivity(changePage)
        }
        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
