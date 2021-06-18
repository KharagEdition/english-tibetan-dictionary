package com.kharagedition.englishtibetandictionary

import com.kharagedition.englishtibetandictionary.adapter.WordsPagingDataAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kharagedition.englishtibetandictionary.model.Word
import com.kharagedition.englishtibetandictionary.util.BottomSheetDialog
import com.kharagedition.englishtibetandictionary.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        var TAG = MainActivity::class.java.name
    }
    lateinit var commonToolbar: MaterialToolbar;
    private lateinit var viewModel: WordsViewModel
    lateinit var wordRecyclerView: RecyclerView;
    private val pagingAdapter by lazy { WordsPagingDataAdapter() }



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
    }

    private fun addListener() {

        wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context);
            adapter = pagingAdapter
            setHasFixedSize(true)
        }

        Log.d(TAG, "addListener: ");
        lifecycleScope.launch {
            viewModel.prayersList.collectLatest { source -> pagingAdapter.submitData(source) }
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

}