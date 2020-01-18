package net.kelmer.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.kelmer.android.common.resolve
import net.kelmer.android.flickrsearch.R
import net.kelmer.android.util.ServiceLocator
import net.kelmer.android.util.ViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private val photoAdapter = PhotoListAdapter()
    private lateinit var serviceLocator: ServiceLocator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceLocator = ServiceLocator.instance()


        viewModel = ViewModelProviders.of(this, ViewModelFactory(serviceLocator.getRepository()))
            .get(MainViewModel::class.java)

        photolist_recyclerview.apply {
//            layoutManager = GridLayoutManager(this@MainActivity, 3)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = photoAdapter
        }

        viewModel.photoLiveData.observe(this, Observer { resource ->
            photolist_progressbar.isVisible = resource.inProgress
            resource?.resolve(
                onError = {
                    Toast.makeText(this, "There was an error!", Toast.LENGTH_SHORT).show();
                    Log.e("SEARCH", "Error", it)
                },
                onSuccess = {
                    photoAdapter.updateList(it)
                }
            )
        })
        viewModel.search("kittens")
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem =  menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        initSearch(searchView)
        return super.onCreateOptionsMenu(menu)
    }


    private fun initSearch(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length > 3) {
                    viewModel.search(newText)
                }
                return true
            }

        })

    }
}
