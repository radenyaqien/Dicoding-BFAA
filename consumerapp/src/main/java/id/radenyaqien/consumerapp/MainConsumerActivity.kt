package id.radenyaqien.consumerapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import id.radenyaqien.consumerapp.databinding.ActivityMainConsumerBinding

class MainConsumerActivity : AppCompatActivity() {

    private val madapter by lazy { MyAdapter() }

    private lateinit var binding: ActivityMainConsumerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_consumer)
        getAllFavoriteUser()
    }

    private fun getAllFavoriteUser() {
        val table = "user_favorite_table"
        val authority = "id.radenyaqien.githubuserdicoding.provider"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            initAdapter(cursor)
        } else {
            initAdapter(cursor)
        }
        cursor?.close()
    }

    private fun initAdapter(cursor: Cursor?) {
        binding.rvMain.apply {

            adapter = madapter
        }
        cursor?.let {
            madapter.addItems(it.toListUserFavorite())
        }

    }


}