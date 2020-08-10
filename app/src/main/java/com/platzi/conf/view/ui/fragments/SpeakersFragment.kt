package com.platzi.conf.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.platzi.conf.R
import com.platzi.conf.model.Conference
import com.platzi.conf.model.Speaker
import com.platzi.conf.view.adapter.ScheduleAdapter
import com.platzi.conf.view.adapter.SpeakerAdapter
import com.platzi.conf.view.adapter.SpeakerListener
import com.platzi.conf.viewmodel.ScheduleViewModel
import com.platzi.conf.viewmodel.SpeakerViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_speakers.*


class SpeakersFragment : Fragment(), SpeakerListener {

    private lateinit var speakerAdapter: SpeakerAdapter
    private lateinit var viewModel: SpeakerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SpeakerViewModel::class.java)
        viewModel.refresh() //Obtiene los datos

        speakerAdapter = SpeakerAdapter(this)
        rvSpeaker.apply {
            layoutManager = GridLayoutManager(context,2) //accede al Grid
            adapter = speakerAdapter
        }
        observeViewModel()
    }

    //observar la lista ante cualquier cambio
    fun observeViewModel(){
        viewModel.listSpeaker.observe(this, Observer<List<Speaker>>{
                speaker -> speakerAdapter.updateData(speaker)

            //Progress Bar
            viewModel.isLoading.observe(this, Observer<Boolean>{
                if (it != null){
                    rlBaseSpeaker.visibility = View.INVISIBLE
                }
            })
        })

    }

    override fun onSpeakerClicked(speaker: Speaker, position: Int) {
        val bundle = bundleOf("Speaker" to speaker)
        findNavController().navigate(R.id.speakersDetailFragmentDialog, bundle)
    }

}