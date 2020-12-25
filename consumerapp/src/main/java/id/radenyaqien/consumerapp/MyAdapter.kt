package id.radenyaqien.consumerapp

import com.squareup.picasso.Picasso
import id.radenyaqien.consumerapp.databinding.ItemFavUserBinding

class MyAdapter : BaseRVAdapter<UserFavorite, ItemFavUserBinding>() {
    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemFavUserBinding>,
        position: Int,
    ) {
        holder.binding.model = items[position]
        Picasso.get()
            .load(items[position].avatarUrl)
            .into(holder.binding.imgAvatar)

    }

    override fun getLayout() = R.layout.item_fav_user
}