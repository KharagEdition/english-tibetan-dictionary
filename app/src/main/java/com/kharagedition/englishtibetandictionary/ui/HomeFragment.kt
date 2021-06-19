package com.kharagedition.englishtibetandictionary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.kharagedition.englishtibetandictionary.R


class HomeFragment : Fragment() {
    lateinit var topAnimation : Animation;
    lateinit var layout: LinearLayout;
    lateinit var materialCardView: MaterialCardView;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home, container, false)
        initView(view);
        initListener();
        topAnimation = AnimationUtils.loadAnimation(context,R.anim.layout_top_anim)
        layout.startAnimation(topAnimation);
        return view;
    }

    private fun initListener() {
        materialCardView.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
        }
    }

    private fun initView(view: View) {
        layout = view.findViewById(R.id.linearLayout)
        materialCardView = view.findViewById(R.id.card_view)


    }


}