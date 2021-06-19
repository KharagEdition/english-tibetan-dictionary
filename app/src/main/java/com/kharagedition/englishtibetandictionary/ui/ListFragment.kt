package com.kharagedition.englishtibetandictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.kharagedition.englishtibetandictionary.MainActivity
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.adapter.WordsPagingDataAdapter
import com.kharagedition.englishtibetandictionary.viewmodel.WordsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    lateinit var mAdView: AdView
    lateinit var wordRecyclerView: RecyclerView;
    private val pagingAdapter by lazy { WordsPagingDataAdapter() }
    private val wordsViewModel: WordsViewModel by activityViewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_list, container, false)
        initViews(view);
        var adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        addListener();
        return view;
    }
    private fun initViews(view: View) {
        wordRecyclerView = view.findViewById(R.id.container);
        mAdView = view.findViewById(R.id.bannerAd1)

    }

    private fun addListener() {

        wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context);
            adapter = pagingAdapter
            setHasFixedSize(true)
        }

        Log.d(MainActivity.TAG, "addListener: ");
        lifecycleScope.launch {
            wordsViewModel.prayersList.collectLatest { source -> pagingAdapter.submitData(source) }
        }
    }

}