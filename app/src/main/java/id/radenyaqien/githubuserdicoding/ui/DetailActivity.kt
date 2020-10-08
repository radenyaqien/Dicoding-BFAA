package id.radenyaqien.githubuserdicoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import id.radenyaqien.githubuserdicoding.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PACKAGE = "extra_package"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val githubUserModel = intent.getParcelableExtra<GithubUserModel>(EXTRA_PACKAGE)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = githubUserModel?.username

        githubUserModel?.let {
            binding.githubUser = it
        }
        githubUserModel?.avatar?.let { binding.ivHero.setImageResource(it) }

    }
}