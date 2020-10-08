package id.radenyaqien.githubuserdicoding.data.retrofit

import id.radenyaqien.githubuserdicoding.data.models.DetailUserResponse
import id.radenyaqien.githubuserdicoding.data.models.FollowResponse
import id.radenyaqien.githubuserdicoding.data.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubInterface {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username: String
    ): List<FollowResponse>

    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username: String
    ): List<FollowResponse>

}