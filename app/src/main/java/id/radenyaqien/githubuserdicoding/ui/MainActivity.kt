package id.radenyaqien.githubuserdicoding

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import id.radenyaqien.githubuserdicoding.util.readAssetsFile
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<GithubUserModel>()
    private val myAdapter by lazy {
        MyAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                   return false
                }
            })
        }


        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initrv()
        setSupportActionBar(materialToolbar)
        supportActionBar!!.setTitle(R.string.app_name)

        val result = assets.readAssetsFile("githubuser.json")

        val jsonObject = JSONObject(result)
        val jsonArray = jsonObject.getJSONArray("users")
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)

            val githubUserModel = GithubUserModel(
                resources.getIdentifier(item.getString("avatar"), "avatar", this.packageName),
                item.getString("company"),
                item.getInt("follower"),
                item.getInt("following"),
                item.getString("location"),
                item.getString("name"),
                item.getInt("repository"),
                item.getString("username")
            )
            list.add(githubUserModel)
            myAdapter.addItems(list)
        }

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