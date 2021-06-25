package com.kharagedition.tibetandictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.SearchAutoComplete
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import com.kharagedition.tibetandictionary.MainActivity
import com.kharagedition.tibetandictionary.R
import com.kharagedition.tibetandictionary.adapter.WordsPagingDataAdapter
import com.kharagedition.tibetandictionary.viewmodel.WordsViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.Field


class ListFragment : Fragment() {
    lateinit var commonToolbar: MaterialToolbar
    lateinit var mAdView: AdView
    lateinit var wordRecyclerView: RecyclerView
    lateinit var loadingProgress: ProgressBar
    lateinit var emptyMessage: MaterialTextView
    private val wordsViewModel: WordsViewModel by activityViewModels()
    private val pagingAdapter by lazy { WordsPagingDataAdapter(wordsViewModel) }
     private var diplayFavWordsFavourite: Boolean? = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        diplayFavWordsFavourite = arguments?.getBoolean("favourite")

        initViews(view)
        setHasOptionsMenu(true)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        addListener()
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        if(diplayFavWordsFavourite==null || diplayFavWordsFavourite == false){
            inflater.inflate(R.menu.option_menu, menu)
            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem?.actionView as SearchView
            val searchTextView: SearchAutoComplete = searchView.findViewById(R.id.search_src_text)
            try {
                val field: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field.set(searchTextView, R.drawable.cursor)
            } catch (e: Exception) {
                // Ignore exception
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        wordsViewModel.filterData(query)
                        wordsViewModel.liveQuery.observe(viewLifecycleOwner, {
                            fetchWordsFromDictionary(query = true)
                        })


                    }
                    Log.i(MainActivity.TAG, "Llego al querysubmit $query")
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {

                    if (query.length > 2) {
                        wordsViewModel.filterData(query)
                        wordsViewModel.liveQuery.observe(viewLifecycleOwner, {
                            fetchWordsFromDictionary(query = true)
                        })
                    }
                    return true
                }
            })
            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }
                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    emptyMessage.visibility = GONE
                    fetchWordsFromDictionary(false);
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
        commonToolbar = view.findViewById(R.id.list_toolbar)
        loadingProgress = view.findViewById(R.id.loading_words)
        emptyMessage = view.findViewById(R.id.empty_msg)
        (activity as AppCompatActivity?)!!.setSupportActionBar(commonToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)


        wordRecyclerView = view.findViewById(R.id.list_container)
        mAdView = view.findViewById(R.id.bannerAd1)
        loadingProgress.visibility = VISIBLE;
        emptyMessage.visibility = GONE;

    }

    private fun addListener() {

        wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagingAdapter
            //edgeEffectFactory = BounceEdgeEffectFactory()
            setHasFixedSize(true)
        }

        Log.d(MainActivity.TAG, "addListener: ")
        fetchWordsFromDictionary(query = false)
        checkEmptyWordListener();

    }

    private fun fetchWordsFromDictionary(query:Boolean) {

        lifecycleScope.launch {
            if(diplayFavWordsFavourite!=null && diplayFavWordsFavourite==true){
                wordsViewModel.favWordsList.collectLatest {
                    if(loadingProgress.isVisible){
                        loadingProgress.visibility = GONE;
                    }
                    pagingAdapter.submitData(it)
                    pagingAdapter.notifyDataSetChanged();
                }
            }else if (query) {
                wordsViewModel.queryWordsList.collectLatest {
                    if (loadingProgress.isVisible) {
                        loadingProgress.visibility = GONE;
                    }
                    pagingAdapter.submitData(it)
                    pagingAdapter.notifyDataSetChanged();
                }
            }else{
                wordsViewModel.wordsList.collectLatest {
                    if(loadingProgress.isVisible){
                        loadingProgress.visibility = GONE;
                    }
                    pagingAdapter.submitData(it)
                    pagingAdapter.notifyDataSetChanged();
                }
            }


        }


    }

    private fun checkEmptyWordListener() {
        lifecycleScope.launch {
            //Your adapter's loadStateFlow here
            pagingAdapter.loadStateFlow.
            distinctUntilChangedBy {
                it.refresh
            }.collect {
                val list = pagingAdapter.snapshot()
                if ( list.size< 1){
                    emptyMessage.visibility= VISIBLE
                }else{
                    emptyMessage.visibility = GONE
                }

            }
        }

    }

}