package id.radenyaqien.githubuserdicoding.data.retrofit


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var items: List<GithubSearchResponse>?,
    @SerializedName("total_count")
    var totalCount: Int?
)