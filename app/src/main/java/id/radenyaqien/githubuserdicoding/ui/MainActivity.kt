package id.radenyaqien.githubuserdicoding.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.adapter.MyAdapter
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.data.retrofit.Service
import id.radenyaqien.githubuserdicoding.ui.viewModels.MainViewModel
import id.radenyaqien.githubuserdicoding.ui.viewModels.ViewModelFactory
import id.radenyaqien.githubuserdicoding.util.snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myAdapter by lazy {
        MyAdapter()
    }
    private var viewModel: MainViewModel? = null
    private lateinit var service: Service
    private lateinit var repository: SearchRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(materialToolbar)
        supportActionBar?.setTitle(R.string.app_name)

        service = Service()
        repository = SearchRepository(service.buildApi(GithubInterface::class.java))
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        progressbar_Main.visibility = View.GONE
        initrv()
        viewModel?.searchResponse?.observe(this, {
            progressbar_Main.visibility = View.GONE

            when (it) {
                is Resource.Success -> {
                    if (it.value.items.isNullOrEmpty()) {
                        root_main.snackbar("User Tidak Di temukan")
                    } else {
                        it.value.items?.let { it1 -> myAdapter.addItems(it1) }
                    }
                }
                is Resource.Failure -> {
                    root_main.snackbar("Periksa Koneksi Anda")
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
                        viewModel?.searchUser(it)
                        progressbar_Main.visibility = View.VISIBLE
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

    private fun initrv() {
        recyclerView.apply {
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