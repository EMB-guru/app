package com.embguru.design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.adupter.categoryAdupter
import com.embguru.design.adupter.categoryListAdupter
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.adupter.viewAllCategoryAdupter
import com.embguru.design.model.categoryListModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.viewAllCategoryModel
import com.embguru.design.storage.category
import com.embguru.design.storage.folderData
import java.util.*
import kotlin.collections.ArrayList

class CategoryPage : AppCompatActivity() {
    private var categoryName: TextView? = null
    private var noItemFound: TextView? = null
    private var Search_View: LinearLayout? = null
    private var Search: EditText? = null
    private var categoryRecyclerview: RecyclerView? = null
    private var FolderData = folderData.getInstance()
    private var SearchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_page)
        define()
    }

    private fun define() {
        categoryName = findViewById(R.id.categoryName)
        noItemFound = findViewById(R.id.noItemFound)
        Search_View = findViewById(R.id.Search_View)
        Search_View?.visibility = View.GONE
        noItemFound?.visibility = View.GONE
        categoryName?.text =intent.getStringExtra("categoryName").toString()
        Search = findViewById(R.id.Search)
        categoryRecyclerview = findViewById(R.id.categoryRecyclerview)

        categoryRecyclerview?.layoutManager =
            object : LinearLayoutManager(applicationContext) {
                override fun canScrollVertically() = true
            }
        setList()

        Search?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                SearchText = s.toString()
                setUpList()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



    }

    private fun setUpList(){
        val allFolderList = FolderData.folderList?.filter { it.categoryName ==intent.getStringExtra("categoryName").toString()} // Get data
        val mySet = mutableSetOf<String>()
        if (allFolderList != null) {
            for (item in allFolderList) {
                mySet.add(item.type)
            }
        }
        val data = ArrayList<viewAllCategoryModel>()

        for (item in mySet) {
            if (allFolderList != null) {
                data.add(
                    viewAllCategoryModel(
                        item,
                        allFolderList.filter { it.type== item && (SearchText=="" || it.folderName.lowercase(Locale.ROOT).contains(SearchText.lowercase(Locale.ROOT)) || it.categoryName.lowercase(Locale.ROOT).contains(SearchText.lowercase(Locale.ROOT)) )} as ArrayList<folderViewModel>
                    )
                )
            }
        }

        val filterData = data.filter { it.list.size != 0 }

        if(filterData.isNotEmpty())
        {
            Search_View?.visibility = View.VISIBLE
            noItemFound?.visibility = View.GONE
        }
        else{
            Search_View?.visibility = View.VISIBLE
            noItemFound?.visibility = View.VISIBLE
        }

        val adapter = viewAllCategoryAdupter(this,data.filter { it.list.size!=0 })
        categoryRecyclerview?.adapter = adapter
    }

    private fun setList() {
        setUpList()
        FolderData.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                setUpList()
            }
        })

    }

    public fun onBackClick(view: View){
        finish()
    }

}
