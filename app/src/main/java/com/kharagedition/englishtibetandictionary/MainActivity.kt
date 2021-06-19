package com.kharagedition.englishtibetandictionary

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.kharagedition.englishtibetandictionary.provider.MySuggestionProvider
import com.kharagedition.englishtibetandictionary.util.BottomSheetDialog
import com.kharagedition.englishtibetandictionary.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        var TAG = MainActivity::class.java.name
    }
    lateinit var commonToolbar: MaterialToolbar;
    private lateinit var viewModel: WordsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
            }
        }

        initViews();

    }
    private fun initViews() {
        commonToolbar = findViewById(R.id.common_toolbar);
        viewModel = ViewModelProvider(this).get(WordsViewModel::class.java)
        setSupportActionBar(commonToolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        var searchItem = menu?.findItem(R.id.action_search);
        var searchView = searchItem?.actionView as SearchView;
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterData(query);
                Log.i(TAG,"Llego al querysubmit $query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i(TAG,"Llego al querytextchange")
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.about_us ->{
                val sheet =  BottomSheetDialog();
                sheet.show(supportFragmentManager,"ModalBottomSheet");
            }
            R.id.exit ->{
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}