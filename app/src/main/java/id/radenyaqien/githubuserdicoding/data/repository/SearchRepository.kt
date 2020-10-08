package id.radenyaqien.githubuserdicoding.data.repository

import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface

class searchRepository( private val api: GithubInterface) : BaseRepository() {

    suspend fun SearchUser(
        username: String,
    ) = SafeApiCall {
        api.SearchUser(username)
    }
}