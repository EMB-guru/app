package com.embguru.design

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.adupter.requirementAdupter
import com.embguru.design.model.requirementViewModel

class RequrmentFragment : Fragment() {

    private var requirements_recyclerview: RecyclerView? = null
    private var Designer_layout: LinearLayout? = null
    private var createRequirementBtn: LinearLayout? = null
    private var Designer_text: TextView? = null
    private var Work_layout: LinearLayout? = null
    private var Work_text: TextView? = null



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
            crateRequirement()
        }
    }

    private fun setRequirement() {

        val data = ArrayList<requirementViewModel>()
        data.add(
            requirementViewModel(
                "ID : hcg3rrfd35",
                "12/10/2022 10:30 Am",
                "Requirement of Designer",
                "Pending"
            )
        )
        data.add(
            requirementViewModel(
                "ID : hcg3rrfeyfdg",
                "12/10/2022 10:30 Pm",
                "Requirement of Work",
                "Pending"
            )
        )
        data.add(
            requirementViewModel(
                "ID : hcg3rrfd35",
                "12/10/2022 10:30 Am",
                "Requirement of Designer",
                "Viewed"
            )
        )
        val adapter = requirementAdupter(data)
        requirements_recyclerview?.adapter = adapter
    }

    private fun onDesignButtonActive(){
        Designer_layout?.background= AppCompatResources.getDrawable(requireActivity().application,R.drawable.active_feild)
        Designer_text?.setTextColor(AppCompatResources.getColorStateList(requireActivity().application,R.color.purple_200))
        Work_layout?.background= AppCompatResources.getDrawable(requireActivity().application,R.drawable.disactive_feild)
        Work_text?.setTextColor(AppCompatResources.getColorStateList(requireActivity().application,R.color.teal_900))
    }

    private fun onWorkButtonActive(){
        Work_layout?.background= AppCompatResources.getDrawable(requireActivity().application,R.drawable.active_feild)
        Work_text?.setTextColor(AppCompatResources.getColorStateList(requireActivity().application,R.color.purple_200))
        Designer_layout?.background= AppCompatResources.getDrawable(requireActivity().application,R.drawable.disactive_feild)
        Designer_text?.setTextColor(AppCompatResources.getColorStateList(requireActivity().application,R.color.teal_900))
    }

    private fun crateRequirement(){

    }

}