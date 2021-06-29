package com.kharagedition.tibetandictionary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.kharagedition.tibetandictionary.R


class MLSearchFragment : Fragment() {
    lateinit var backBtn:ImageView;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_m_l_search, container, false)
        backBtn = view.findViewById(R.id.ml_backbtn);
        initVew(view);
        initListener()
        return  view;
    }

    private fun initListener() {
        backBtn.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    private fun initVew(view: View) {
        backBtn = view.findViewById(R.id.ml_backbtn);
    }



}