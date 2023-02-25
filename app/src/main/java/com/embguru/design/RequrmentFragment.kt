package com.embguru.design

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.adupter.folderAdupter
import com.embguru.design.adupter.requirementAdupter
import com.embguru.design.model.requirementViewModel
import com.embguru.design.storage.folderData
import com.embguru.design.storage.requirementData
import com.embguru.design.storage.userData
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RequrmentFragment : Fragment() {

    private var requirements_recyclerview: RecyclerView? = null
    private var Designer_layout: LinearLayout? = null
    private var createRequirementBtn: LinearLayout? = null
    private var Designer_text: TextView? = null
    private var Work_layout: LinearLayout? = null
    private var Work_text: TextView? = null
    private var flag = true
    private var userdata: userData? = userData.getInstance()
    private val requirementList = requirementData.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requrment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requirements_recyclerview = view.findViewById(R.id.requirements_recyclerview)
        createRequirementBtn = view.findViewById(R.id.createRequirementBtn)
        requirements_recyclerview?.layoutManager =
            object : LinearLayoutManager(activity) {
                override fun canScrollVertically() = false
            }
        Designer_layout = view.findViewById(R.id.Designer_layout)
        Designer_text = view.findViewById(R.id.Designer_text)
        Work_layout = view.findViewById(R.id.Work_layout)
        Work_text = view.findViewById(R.id.Work_text)
        setRequirement()
        Designer_layout?.setOnClickListener {
            onDesignButtonActive()
        }
        Work_layout?.setOnClickListener {
            onWorkButtonActive()
        }
        createRequirementBtn?.setOnClickListener {
            crateRequirement(view.context)
        }
    }

    private fun setRequirement() {

        requirements_recyclerview?.adapter = requirementList.requirementList?.let {
            requirementAdupter(
                it
            )
        }
        Log.e("UpdateLog", requirementList.requirementList.toString())
        requirementList.addObserver(object : Observer {
            override fun update(o: Observable?, arg: Any?) {
                requirements_recyclerview?.adapter = requirementList.requirementList?.let {
                    requirementAdupter(
                        it
                    )
                }
            }
        })
    }

    private fun onDesignButtonActive() {
        Designer_layout?.background =
            AppCompatResources.getDrawable(requireActivity().application, R.drawable.active_feild)
        Designer_text?.setTextColor(
            AppCompatResources.getColorStateList(
                requireActivity().application,
                R.color.purple_200
            )
        )
        Work_layout?.background = AppCompatResources.getDrawable(
            requireActivity().application,
            R.drawable.disactive_feild
        )
        Work_text?.setTextColor(
            AppCompatResources.getColorStateList(
                requireActivity().application,
                R.color.teal_900
            )
        )
        flag = true
    }

    private fun onWorkButtonActive() {
        Work_layout?.background =
            AppCompatResources.getDrawable(requireActivity().application, R.drawable.active_feild)
        Work_text?.setTextColor(
            AppCompatResources.getColorStateList(
                requireActivity().application,
                R.color.purple_200
            )
        )
        Designer_layout?.background = AppCompatResources.getDrawable(
            requireActivity().application,
            R.drawable.disactive_feild
        )
        Designer_text?.setTextColor(
            AppCompatResources.getColorStateList(
                requireActivity().application,
                R.color.teal_900
            )
        )
        flag = false


    }

    private fun AddData(){
        val updates = HashMap<String, Any>()
        if (flag) {
            updates["name"] = "Designer"
        } else {
            updates["name"] = "Work"
        }
        updates["date"] = Date().toString()
        updates["status"] = "Pending"
        updates["mobileNumber"] = "${userdata?.phoneNumber}"

        val requestRef = FirebaseDatabase.getInstance().reference.child("request")
        requestRef.push().setValue(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "Your request is created. our team come back to you soon",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // Verification failed
                Toast.makeText(
                    context,
                    "Something went wrong try again",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }

    private fun crateRequirement(context: Context) {
        var openRequest:  List<requirementViewModel>?= null
        openRequest = if (flag) {
            requirementList.requirementList?.filter { it->it.name=="Requirement of Designer" && it.status == "Pending" }
        } else {
            requirementList.requirementList?.filter {  it->it.name=="Requirement of Work" && it.status == "Pending"}

        }


        if (openRequest != null) {
            if(openRequest.isEmpty())
            {
                AddData()
            }else{
                if (flag) {
                    Toast.makeText(
                        context,
                        "Your request for Designer is already open ",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Your request for Work is already open ",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }else{
            AddData()
        }


    }

}