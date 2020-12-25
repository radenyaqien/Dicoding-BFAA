package id.radenyaqien.githubuserdicoding.util

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import id.radenyaqien.githubuserdicoding.persistence.UserDao


class MyContentProvider : ContentProvider() {


    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface LogsContentProviderEntryPoint {
        fun userFavoriteDao(): UserDao
    }

    companion object {
        private const val USER = 1
        private const val AUTHORITY = "id.radenyaqien.githubuserdicoding.provider"
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "user_favorite_table", USER)
        }
    }

    private fun getuserFavoriteDao(appContext: Context): UserDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            LogsContentProviderEntryPoint::class.java
        )
        return hiltEntryPoint.userFavoriteDao()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?,
    ): Cursor? {
        return when (sUriMatcher.match(p0)) {
            USER -> context?.let { getuserFavoriteDao(it).cursorGetAll() }
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }
}