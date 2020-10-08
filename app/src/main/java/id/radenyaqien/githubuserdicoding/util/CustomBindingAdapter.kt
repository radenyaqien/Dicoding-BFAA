package id.radenyaqien.githubuserdicoding.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import id.radenyaqien.githubuserdicoding.R


@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {

        Picasso.get()
            .load(url)
            .error(R.drawable.no_image)
            .placeholder(R.drawable.no_image)
            .into(this)
    }
}

