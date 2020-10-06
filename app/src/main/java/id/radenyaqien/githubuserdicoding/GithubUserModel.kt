package id.radenyaqien.githubuserdicoding


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUserModel(
    @SerializedName("avatar")
    var avatar: Int,
    @SerializedName("company")
    var company: String,
    @SerializedName("follower")
    var follower: Int,
    @SerializedName("following")
    var following: Int,
    @SerializedName("location")
    var location: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("repository")
    var repository: Int,
    @SerializedName("username")
    var username: String
) : Parcelable