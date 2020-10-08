package id.radenyaqien.githubuserdicoding.adapter

import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.data.models.GithubSearchResponse

import id.radenyaqien.githubuserdicoding.databinding.ItemRvUserBinding

class MyAdapter : BaseRVAdapter<GithubSearchResponse, ItemRvUserBinding>() {
    override fun getLayout() = R.layout.item_rv_user

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemRvUserBinding>,
        position: Int
    ) {
        holder.binding.githubUser = items[position]
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }
}