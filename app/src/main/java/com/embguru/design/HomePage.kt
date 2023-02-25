package com.embguru.design

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.adupter.categoryAdupter
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val bottomNavigationView:BottomNavigationView=findViewById(R.id.bottomNavigationView)

        setCurrentFragment(homeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Home->setCurrentFragment(homeFragment())
                R.id.Favourite->setCurrentFragment(favouriteFragment())
                R.id.Requirements->setCurrentFragment(RequrmentFragment())
                R.id.Downloads->setCurrentFragment(DownloadFragment())
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,fragment)
            commit()
        }


}