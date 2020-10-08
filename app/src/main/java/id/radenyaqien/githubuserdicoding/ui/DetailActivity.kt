package id.radenyaqien.githubuserdicoding.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.data.models.GithubSearchResponse
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.data.retrofit.Service
import id.radenyaqien.githubuserdicoding.databinding.ActivityDetailBinding
import id.radenyaqien.githubuserdicoding.ui.viewModels.MainViewModel
import id.radenyaqien.githubuserdicoding.ui.viewModels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PACKAGE = "extra_package"
    }

    private lateinit var binding: ActivityDetailBinding
    private var viewModel: MainViewModel? = null
    private lateinit var service: Service
    private lateinit var repository: SearchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        service = Service()
        repository = SearchRepository(service.buildApi(GithubInterface::class.java))
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        val githubUserModel = intent.getParcelableExtra<GithubSearchResponse>(EXTRA_PACKAGE)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = githubUserModel?.login

        githubUserModel?.login?.let {
            viewModel?.getDetailUser(it)
            initViewPager(it)
        }

        viewModel?.detailResponse?.observe(this, {
            when (it) {
                is Resource.Success -> {
                    binding.githubSearch = it.value
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "User tidak di temukan", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    private fun initViewPager(username: String) {
        viewPager.adapter = object : FragmentStateAdapter(this) {
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

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Follower"
                else -> "Following"
            }

        }.attach()
    }
}