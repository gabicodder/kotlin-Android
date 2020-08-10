package com.platzi.conf.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.platzi.conf.R
import com.platzi.conf.model.Conference
import com.platzi.conf.view.adapter.ScheduleAdapter
import com.platzi.conf.view.adapter.ScheduleListener
import com.platzi.conf.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment(), ScheduleListener {

    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule,container,false)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        viewModel.refresh() //Obtiene los datos

        scheduleAdapter = ScheduleAdapter(this)
        rvSchedule.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false) //accede al LinearLayout
            adapter = scheduleAdapter
        }
        observeViewModel()
    }

    //observar la lista ante cualquier cambio
    fun observeViewModel(){
        viewModel.listSchedule.observe(this, Observer<List<Conference>>{
            schedule -> scheduleAdapter.updateData(schedule)

            //Progress Bar
            viewModel.isLoading.observe(this, Observer<Boolean>{
                if (it != null){
                    rlBaseSchedule.visibility = View.INVISIBLE
                }
            })
        })

    }

    override fun onConferenceClicked(conference: Conference, position: Int) {
        val bundle = bundleOf("Conference" to conference)
        findNavController().navigate(R.id.ScheduleDetailFragmentDialog, bundle)
    }

    }

