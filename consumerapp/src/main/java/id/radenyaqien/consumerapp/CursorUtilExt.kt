package id.radenyaqien.consumerapp

import android.database.Cursor


fun Cursor.toListUserFavorite(): ArrayList<UserFavorite> {
    val userFavoriteList = ArrayList<UserFavorite>()

    apply {
        while (moveToNext()) {
            userFavoriteList.add(
                toUserFavoriteEntity()
            )
        }
    }

    return userFavoriteList
}

fun Cursor.toUserFavoriteEntity(): UserFavorite =
    UserFavorite(
        getString(getColumnIndexOrThrow("username")),
        getString(getColumnIndexOrThrow("name")),
        getString(getColumnIndexOrThrow("avatar_url")),
        getString(getColumnIndexOrThrow("following_url")),
        getString(getColumnIndexOrThrow("bio")),
        getString(getColumnIndexOrThrow("company")),
        getInt(getColumnIndexOrThrow("public_repos")),
        getString(getColumnIndexOrThrow("followers_url")),
        getInt(getColumnIndexOrThrow("followers")),
        getInt(getColumnIndexOrThrow("following")),
        getString(getColumnIndexOrThrow("location"))
    )