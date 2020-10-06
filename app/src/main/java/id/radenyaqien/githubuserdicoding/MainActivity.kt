package id.radenyaqien.githubuserdicoding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<GithubUserModel>()
    private val myAdapter by lazy {
        MyAdapter()
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