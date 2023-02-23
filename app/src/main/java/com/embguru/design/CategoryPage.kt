package com.embguru.design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.adupter.categoryListAdupter
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.adupter.viewAllCategoryAdupter
import com.embguru.design.model.categoryListModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.viewAllCategoryModel

class CategoryPage : AppCompatActivity() {
    private var categoryName: TextView? = null
    private var Search: EditText? = null
    private var categoryRecyclerview: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_page)
        define()
    }

    private fun define() {
        categoryName = findViewById(R.id.categoryName)
        categoryName?.text =intent.getStringExtra("categoryName").toString()
        Search = findViewById(R.id.Search)
        categoryRecyclerview = findViewById(R.id.categoryRecyclerview)

        categoryRecyclerview?.layoutManager =
            object : LinearLayoutManager(applicationContext) {
                override fun canScrollVertically() = true
            }
        setList()
    }


    private fun setList() {

        val data = ArrayList<viewAllCategoryModel>()
        val dataList = ArrayList<folderViewModel>()
        dataList.add(
            folderViewModel(
                "Movie",
                "Folder 1",
                "https://firebasestorage.googleapis.com/v0/b/embgurufirebase.appspot.com/o/Girnar%202.zip?alt=media&token=4dee8e8d-7e5a-443e-88c9-4c1a69868a01",
                "gs://embgurufirebase.appspot.com",
                "2W ago"
            )
        )
        dataList.add(
            folderViewModel(
                "Movie2",
                "Folder 2",
                "https://firebasestorage.googleapis.com/v0/b/book-af6b7.appspot.com/o/A%20Complete%20Guide%20to%20Programming%20in%20C%2B%2B.pdf?alt=media&token=edaf5518-3565-43f5-b5d6-035c9dd26ca8",
                "gs://book-af6b7.appspot.com",
                "3W ago"
            )
        )
        dataList.add(
            folderViewModel(
                "Movie3",
                "Folder 3",
                "https://firebasestorage.googleapis.com/v0/b/book-af6b7.appspot.com/o/C%20Language%20Tutorial.pdf?alt=media&token=3ecd52cb-ad13-4dc2-9dad-2a8f5e576fec",
                "gs://book-af6b7.appspot.com",
                "4W ago"
            )
        )
        dataList.add(
            folderViewModel(
                "Movie4",
                "Folder 4",
                "https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg",
                "gs://embgurufirebase.appspot.com",
                "5W ago"
            )
        )
        data.add(
            viewAllCategoryModel(
                "Sadi",
                dataList
            )
        )

        data.add(
            viewAllCategoryModel(
                "Lehng",
                dataList
            )
        )

        data.add(
            viewAllCategoryModel(
                "Kurta",
                dataList
            )
        )

        data.add(
            viewAllCategoryModel(
                "Dupatta",
                dataList
            )
        )

        val adapter = viewAllCategoryAdupter(applicationContext,data)
        categoryRecyclerview?.adapter = adapter
    }

    public fun onBackClick(view: View){
        finish()
    }

}
