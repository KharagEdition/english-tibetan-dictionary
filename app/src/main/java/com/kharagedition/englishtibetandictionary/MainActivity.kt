package com.kharagedition.englishtibetandictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kharagedition.englishtibetandictionary.adapter.ItemClickListener
import com.kharagedition.englishtibetandictionary.adapter.WordsAdapter
import com.kharagedition.englishtibetandictionary.model.Word
import com.kharagedition.englishtibetandictionary.util.BottomSheetDialog
import com.kharagedition.englishtibetandictionary.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    companion object{
        var TAG = MainActivity::class.java.name
    }
    lateinit var commonToolbar: MaterialToolbar;
    private lateinit var viewModel: WordsViewModel
    lateinit var wordRecyclerView: RecyclerView;
    lateinit var wordsAdapter: WordsAdapter;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews();
        addListener();

    }
    private fun initViews() {
        commonToolbar = findViewById(R.id.common_toolbar);
        wordRecyclerView = findViewById(R.id.container);
        viewModel = ViewModelProvider(this).get(WordsViewModel::class.java)
        setSupportActionBar(commonToolbar)
        wordsAdapter= WordsAdapter(this)
    }

    private fun addListener() {

        wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context);
            adapter = wordsAdapter
            setHasFixedSize(true)
        }

        Log.d(TAG, "addListener: ");
        viewModel.prayers.observe(this
        ) {
            if(it.isEmpty()){
                Log.d(TAG, "empty: ");
            }else{
                wordsAdapter.submitList(it)
                Log.d(TAG, "SIZE:::${it.size}");
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
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

    override fun onItemClick(word: Word) {
        TODO("Not yet implemented")
    }
}