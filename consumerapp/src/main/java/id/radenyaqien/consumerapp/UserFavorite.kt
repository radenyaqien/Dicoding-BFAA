package id.radenyaqien.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize

data class UserFavorite(
    val username: String,
    val name: String?,
    val avatarUrl: String?,
    val followingUrl: String?,
    val bio: String?,
    val company: String?,
    val publicRepos: Int?,
    val followersUrl: String?,
    val followers: Int?,
    val following: Int?,
    val location: String?,
) : Parcelable
