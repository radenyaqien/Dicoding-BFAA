package id.radenyaqien.githubuserdicoding.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.adapter.Fav_adapter
import id.radenyaqien.githubuserdicoding.data.models.GithubSearchResponse
import id.radenyaqien.githubuserdicoding.databinding.ActivityFavoriteBinding
import id.radenyaqien.githubuserdicoding.persistence.FavoriteViewModel
import id.radenyaqien.githubuserdicoding.persistence.UserFavorite

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private var user: GithubSearchResponse? = null
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private val mAdapter by lazy {
        Fav_adapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        recyclerView()
        setToolbar()
        viewModel._favUser.observe(this) {
            if (!it.isNullOrEmpty()) {
                mAdapter.addItems(it)
            } else {
                binding.imgNodata.visibility = View.VISIBLE
                mAdapter.clearItems()
            }

        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.title = getString(R.string.fav_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener { finish() }
    }

    private fun recyclerView() {
        binding.rvMain.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }

        mAdapter.listener = { view: View, item: UserFavorite, position: Int ->
            user = GithubSearchResponse(
                item.avatarUrl,
                "",
                item.followersUrl,
                item.followingUrl,
                "",
                "",
                "",
                0,
                item.username,
                "",
                "",
                "",
                "",
                0,
                true,
                "",
                "",
                "",
                ""
            )

            startActivity(
                Intent(
                    this,
                    DetailActivity::class.java
                ).putExtra(DetailActivity.EXTRA_PACKAGE, user)
            )
        }
    }

}