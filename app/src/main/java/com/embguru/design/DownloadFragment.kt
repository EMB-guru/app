package com.embguru.design

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.adupter.categoryAdupter
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.helper.getDatediff
import com.embguru.design.storage.category
import com.embguru.design.storage.folderData
import java.util.*

class DownloadFragment : Fragment() {
    private var boolean_permission = true
    private var REQUEST_PERMISSIONS = 1
    private var NewProductRecyclerView: RecyclerView? = null
    private var backBtn: LinearLayout? = null
    private var noItemFound: TextView? = null
    private var FolderData = folderData.getInstance()
    private var Helper = getDatediff()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewProductRecyclerView = view.findViewById<RecyclerView>(R.id.new_item_recyclerview)
        backBtn = view.findViewById(R.id.backBtn)
        noItemFound = view.findViewById(R.id.noItemFound)
        NewProductRecyclerView?.layoutManager =
            object : LinearLayoutManager(activity) {
                override fun canScrollVertically() = false
            }
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

    private fun setData(context: Context) {
        val data =
            FolderData.folderList?.filter { it ->
                Helper.fileExistOrNot(
                    it.categoryName,
                    it.folderName
                )
            }

        if (data != null) {
            if(data.isNotEmpty())
            {
                noItemFound?.visibility = View.GONE
            }else{
                noItemFound?.visibility = View.VISIBLE
            }
            val adapter = folderAdupter(context, data)



            NewProductRecyclerView?.adapter = adapter

        }

    }

    private fun setNewInWeek(context: Context) {

        setData(context)
        FolderData.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                setData(context)
            }
        })

    }
}