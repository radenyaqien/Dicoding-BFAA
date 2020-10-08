package id.radenyaqien.githubuserdicoding.adapter

import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.data.models.FollowResponse
import id.radenyaqien.githubuserdicoding.databinding.ItemRvFollowBinding

class FollowAdapter : BaseRVAdapter<FollowResponse, ItemRvFollowBinding>() {
    override fun getLayout() = R.layout.item_rv_follow

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemRvFollowBinding>,
        position: Int
    ) {
        holder.binding.follower = items[position]
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }
}