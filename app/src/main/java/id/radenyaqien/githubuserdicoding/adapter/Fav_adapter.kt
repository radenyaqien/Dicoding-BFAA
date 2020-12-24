package id.radenyaqien.githubuserdicoding.adapter

import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.databinding.ItemFavUserBinding
import id.radenyaqien.githubuserdicoding.persistence.UserFavorite

class Fav_adapter : BaseRVAdapter<UserFavorite, ItemFavUserBinding>() {
    override fun getLayout() = R.layout.item_fav_user

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemFavUserBinding>,
        position: Int
    ) {
        val model = items[position]
        holder.binding.model = model

        holder.binding.root.setOnClickListener {
            listener?.invoke(it, model, position)
        }
    }
}