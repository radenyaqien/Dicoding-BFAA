package id.radenyaqien.githubuserdicoding.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.data.models.DetailUserResponse
import id.radenyaqien.githubuserdicoding.data.models.GithubSearchResponse
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.databinding.ActivityDetailBinding
import id.radenyaqien.githubuserdicoding.persistence.UserFavorite
import id.radenyaqien.githubuserdicoding.ui.viewModels.DetailViewModel

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PACKAGE = "extra_package"
    }

    private var isFavorite = false
    private var username: String? = null
    private var user: DetailUserResponse? = null
    private var detailuser: UserFavorite? = null
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        handlingIntent()
        observer()
        username?.let {
            viewModel.getFavUserByUsername(it).observe(this) { user1 ->
                handleUserDetailFromDb(user1.firstOrNull())
            }
        }
        binding.fabButton.setOnClickListener {
            setFavoriteUser()
        }
    }

    private fun observer() {
        viewModel.resultDeleteUserDb.observe(this) {
            if (it) Toast.makeText(
                this,
                "berhasil di tambahkan ke user Favorite",
                Toast.LENGTH_SHORT
            ).show() else Toast.makeText(
                this,
                "gagal di tambahkan ke user Favorite",
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.resultInsertUserDb.observe(this, {
            if (it) username?.let { user ->
                viewModel.getFavUserByUsername(user).observe(this) { user1 ->
                    handleUserDetailFromDb(user1.firstOrNull())
                }
            }

        })

        viewModel.detailResponse.observe(this, {
            when (it) {
                is Resource.Success -> {
                    binding.githubSearch = it.value
                    user = it.value
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "User tidak di temukan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handlingIntent() {
        val githubUserModel = intent.getParcelableExtra<GithubSearchResponse>(EXTRA_PACKAGE)
        username = githubUserModel?.login
        githubUserModel?.let {
            getData(it)
            setToolbar(it)
        }

    }

    private fun setToolbar(githubUserModel: GithubSearchResponse) {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.title = githubUserModel.login
    }

    private fun getData(githubUserModel: GithubSearchResponse) {
        githubUserModel.login?.let {
            viewModel.getDetailUser(it)
            initViewPager(it)
        }
    }

    private fun setFavoriteUser() {
        if (isFavorite) {
            detailuser?.let {
                viewModel.deleteUserFromDb(userFavorite = it)
            }

        } else {
            detailuser = user?.login?.let { it1 ->
                UserFavorite(
                    it1,
                    user?.name,
                    user?.avatarUrl,
                    user?.followingUrl,
                    user?.bio,
                    user?.company,
                    user?.publicRepos,
                    user?.followersUrl,
                    user?.followers,
                    user?.following,
                    user?.location
                )
            }

            detailuser?.let { it1 ->
                viewModel.insertToDB(
                    it1
                )
            }

        }
    }

    private fun handleUserDetailFromDb(userFavorite: UserFavorite?) {
        if (userFavorite == null) {
            isFavorite = false
            val icon = R.drawable.ic_baseline_favorite_border_24
            binding.fabButton.setImageResource(icon)
        } else {
            detailuser = userFavorite
            isFavorite = true
            val icon = R.drawable.ic_baseline_favorite_24
            binding.fabButton.setImageResource(icon)
        }
    }

    private fun initViewPager(username: String) {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        MyFragment.newInstance("follower", username)
                    }
                    else -> {
                        MyFragment.newInstance("following", username)
                    }
                }
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Follower"
                else -> "Following"
            }

        }.attach()
    }
}