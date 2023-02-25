package com.embguru.design

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.embguru.design.adupter.categoryAdupter
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.helper.getDatediff
import com.embguru.design.storage.appData
import com.embguru.design.storage.category
import com.embguru.design.storage.folderData
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class homeFragment : Fragment() {
    private var boolean_permission = true
    private var REQUEST_PERMISSIONS = 1
    private var imageSlider: ImageSlider? = null
    private var categoryRecyclerView: RecyclerView? = null
    private var NewProductRecyclerView: RecyclerView? = null
    private val APPDATA = appData.getInstance()
    private val categoryData = category.getInstance()
    private val folder = folderData.getInstance()
    private val DateClass = getDatediff()
    private var SearchText = ""
    private var Search: EditText? = null
    private var Search_View: LinearLayout? = null
    private var logOut: LinearLayout? = null
    private var noItemFound: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        categoryRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview);
        NewProductRecyclerView = view.findViewById<RecyclerView>(R.id.new_item_recyclerview)
        NewProductRecyclerView?.layoutManager =
            object : LinearLayoutManager(activity) {
                override fun canScrollVertically() = false
            }
        categoryRecyclerView?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        Search = view.findViewById(R.id.Search)
        noItemFound = view.findViewById(R.id.noItemFound)
        Search_View = view.findViewById(R.id.Search_View)
        logOut = view.findViewById(R.id.logOut)

        logOut?.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            val changePage = Intent(view.context, Login::class.java)
            startActivity(changePage)
            requireActivity().finish()
        }



        Search?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                SearchText = s.toString()
                setNewInWeekList(view.context)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        setImages()
        setCategory()
        setNewInWeek(view.context)
        fn_permission()
    }

    private fun fn_permission() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireActivity().applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf<String>(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            boolean_permission = true

        }
    }


    private fun setImages() {
        APPDATA.Poster?.let { imageSlider?.setImageList(it, ScaleTypes.CENTER_CROP) }
        APPDATA.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                APPDATA.Poster?.let { imageSlider?.setImageList(it, ScaleTypes.CENTER_CROP) }
            }
        })
    }

    private fun setCategoryList() {
        val data = categoryData.categoryData // Get data
        val adapter = data?.let { categoryAdupter(it) }
        categoryRecyclerView?.adapter = adapter
    }

    private fun setCategory() {
        setCategoryList()
        categoryData.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                setCategoryList()
            }
        })
    }

    private fun setNewInWeekList(context: Context) {
        val filterList = folder.folderList?.filter { it -> DateClass.getTimeDifferenceInDay(it.time) <= 7&& (SearchText=="" || it.folderName.lowercase(Locale.ROOT).contains(SearchText.lowercase(Locale.ROOT)) || it.categoryName.lowercase(Locale.ROOT).contains(SearchText.lowercase(Locale.ROOT)) ) }

        if (filterList != null) {
            if(filterList.isNotEmpty())
            {
                noItemFound?.visibility = View.GONE
            }else{
                noItemFound?.visibility = View.VISIBLE
            }
            NewProductRecyclerView?.adapter = folderAdupter( context, filterList)


        }else{
            noItemFound?.visibility = View.VISIBLE
        }

    }

    private fun setNewInWeek(context: Context) {
        setNewInWeekList(context)
        folder.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                setNewInWeekList(context)
            }
        })
    }


}