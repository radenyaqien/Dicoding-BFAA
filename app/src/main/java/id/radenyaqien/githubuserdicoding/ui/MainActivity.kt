package id.radenyaqien.githubuserdicoding.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.adapter.MyAdapter
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.databinding.ActivityMainBinding
import id.radenyaqien.githubuserdicoding.ui.viewModels.MainViewModel
import id.radenyaqien.githubuserdicoding.util.snackbar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val myAdapter by lazy {
        MyAdapter()
    }
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setTitle(R.string.app_name)
        binding.progressbarMain.visibility = View.GONE
        initrv()
        viewModel.searchResponse.observe(this, {
            binding.progressbarMain.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.items.isNullOrEmpty()) {
                        binding.imgNodata.visibility = View.VISIBLE
                        binding.rootMain.snackbar("User Tidak Di temukan")
                    } else {
                        it.value.items?.let { it1 -> myAdapter.addItems(it1) }
                    }
                }
                is Resource.Failure -> {


                    binding.rootMain.snackbar("Periksa Koneksi Anda")
                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.searchUser(it)
                        binding.progressbarMain.visibility = View.VISIBLE
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Setting -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.favourite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initrv() {
        binding.recyclerView.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = myAdapter
        }

        myAdapter.listener = { _, item, _ ->

            startActivity(
                Intent(
                    this,
                    DetailActivity::class.java
                ).putExtra(DetailActivity.EXTRA_PACKAGE, item)
            )
        }
    }

}