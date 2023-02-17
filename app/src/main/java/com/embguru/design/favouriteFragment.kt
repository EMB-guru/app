package com.embguru.design

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.model.folderViewModel

class favouriteFragment : Fragment() {
    private var boolean_permission = true
    private var REQUEST_PERMISSIONS = 1
    private var NewProductRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewProductRecyclerView = view.findViewById<RecyclerView>(R.id.new_item_recyclerview)
        NewProductRecyclerView?.layoutManager =
            object : LinearLayoutManager(activity) {
                override fun canScrollVertically() = false
            }
        setNewInWeek()
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

    private fun setNewInWeek() {

        val data = ArrayList<folderViewModel>()
        data.add(
            folderViewModel(
                "Movie",
                "Avengers Endgame ",
                "https://firebasestorage.googleapis.com/v0/b/embgurufirebase.appspot.com/o/Girnar%202.zip?alt=media&token=4dee8e8d-7e5a-443e-88c9-4c1a69868a01",
                "gs://embgurufirebase.appspot.com"
            )
        )
        data.add(
            folderViewModel(
                "Movie2",
                "Jumanji",
                "https://firebasestorage.googleapis.com/v0/b/book-af6b7.appspot.com/o/A%20Complete%20Guide%20to%20Programming%20in%20C%2B%2B.pdf?alt=media&token=edaf5518-3565-43f5-b5d6-035c9dd26ca8",
                "gs://book-af6b7.appspot.com"
            )
        )
        data.add(
            folderViewModel(
                "Movie3",
                "Spider Man",
                "https://firebasestorage.googleapis.com/v0/b/book-af6b7.appspot.com/o/C%20Language%20Tutorial.pdf?alt=media&token=3ecd52cb-ad13-4dc2-9dad-2a8f5e576fec",
                "gs://book-af6b7.appspot.com"
            )
        )
        data.add(
            folderViewModel(
                "Movie4",
                "Venom",
                "https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg",
                "gs://embgurufirebase.appspot.com"
            )
        )
        val adapter = folderAdupter(requireActivity(), data)
        NewProductRecyclerView?.adapter = adapter
    }

}