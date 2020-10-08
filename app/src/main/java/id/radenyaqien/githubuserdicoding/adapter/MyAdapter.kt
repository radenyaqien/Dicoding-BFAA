package id.radenyaqien.githubuserdicoding

import id.radenyaqien.githubuserdicoding.databinding.ItemRvHeroBinding

class MyAdapter : BaseRVAdapter<GithubUserModel, ItemRvHeroBinding>() {
    override fun getLayout() = R.layout.item_rv_hero

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemRvHeroBinding>,
        position: Int
    ) {
        holder.binding.githubUser = items[position]


        holder.binding.ivHero.setImageResource(items[position].avatar)
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }
}