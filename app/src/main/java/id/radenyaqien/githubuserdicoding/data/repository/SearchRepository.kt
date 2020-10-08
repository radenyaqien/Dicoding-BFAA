package id.radenyaqien.githubuserdicoding.data.repository

import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface

class SearchRepository(private val api: GithubInterface) : BaseRepository() {

    suspend fun SearchUser(
        username: String
    ) = safeApiCall {
        api.searchUser(username)
    }

    suspend fun GetDetailUser(
        username: String
    ) = safeApiCall {
        api.getDetailUser(username)
    }

    suspend fun GetFollower(
        username: String
    ) = safeApiCall {
        api.getFollowerUser(username)
    }

    suspend fun GetFollowing(
        username: String
    ) = safeApiCall {
        api.getFollowingUser(username)
    }
}