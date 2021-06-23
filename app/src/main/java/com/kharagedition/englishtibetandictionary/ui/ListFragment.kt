package com.kharagedition.englishtibetandictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.appbar.MaterialToolbar
import com.kharagedition.englishtibetandictionary.MainActivity
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.adapter.WordsPagingDataAdapter
import com.kharagedition.englishtibetandictionary.util.BottomSheetDialog
import com.kharagedition.englishtibetandictionary.util.BounceEdgeEffectFactory
import com.kharagedition.englishtibetandictionary.viewmodel.WordsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    lateinit var commonToolbar: MaterialToolbar;
    lateinit var mAdView: AdView
    lateinit var wordRecyclerView: RecyclerView;
    private val wordsViewModel: WordsViewModel by activityViewModels();
    private val pagingAdapter by lazy { WordsPagingDataAdapter(wordsViewModel) }
     private var diplayFavWordsFavourite: Boolean? = false;
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_list, container, false)
        diplayFavWordsFavourite = arguments?.getBoolean("favourite")

        initViews(view);
        setHasOptionsMenu(true);
        var adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        addListener();
        return view;
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear();
        if(diplayFavWordsFavourite==null || diplayFavWordsFavourite == false){
            inflater.inflate(R.menu.option_menu, menu)
            val searchItem = menu.findItem(R.id.action_search);
            val searchView = searchItem?.actionView as SearchView;
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query!=null){
                        wordsViewModel.filterData(query);
                        wordsViewModel.liveQuery.observe(viewLifecycleOwner, Observer {
                            lifecycleScope.launch {
                                wordsViewModel.queryWordsList.collectLatest {
                                    pagingAdapter.submitData(it)
                                }
                            }
                        })

                    }
                    /*lifecycleScope.launch {
                        wordsViewModel.filteredData(query).collectLatest {
                            pagingAdapter.submitData(it)
                        }
                    }*/
                    Log.i(MainActivity.TAG,"Llego al querysubmit $query")
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if(query.length>2){
                        wordsViewModel.filterData(query);
                        wordsViewModel.liveQuery.observe(viewLifecycleOwner, Observer {
                            lifecycleScope.launch {
                                wordsViewModel.queryWordsList.collectLatest {
                                    pagingAdapter.submitData(it)
                                }
                            }
                        })
                    }
                    return true
                }
            })
        }else{
            commonToolbar.title = "Favourite"
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews(view: View) {
        commonToolbar = view.findViewById(R.id.common_toolbar);
        (activity as AppCompatActivity?)!!.setSupportActionBar(commonToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);


        wordRecyclerView = view.findViewById(R.id.container);
        mAdView = view.findViewById(R.id.bannerAd1)

    }

    private fun addListener() {

        wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context);
            adapter = pagingAdapter
            edgeEffectFactory = BounceEdgeEffectFactory()
            setHasFixedSize(true)
        }

        Log.d(MainActivity.TAG, "addListener: ");
        if(diplayFavWordsFavourite!=null && diplayFavWordsFavourite==true){
            lifecycleScope.launch {
                wordsViewModel.favWordsList.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }else{
            lifecycleScope.launch {
                wordsViewModel.wordsList.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }


    }

}