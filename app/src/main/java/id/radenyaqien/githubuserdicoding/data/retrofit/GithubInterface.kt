package id.radenyaqien.githubuserdicoding.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchUserInterface {
    @GET("search/users")
    suspend fun SearchUser(
        @Query("q") query : String
    ) : MyResponse<GithubSearchResponse>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : DetailUserResponse
}