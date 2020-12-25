package id.radenyaqien.githubuserdicoding.persistence

import android.database.Cursor
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_favorite_table")
    fun getAll(): Flow<List<UserFavorite>>

    @Query("SELECT * FROM user_favorite_table WHERE username = :userName")
    fun getByUserName(userName: String): Flow<List<UserFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserFavorite)

    @Delete
    suspend fun delete(user: UserFavorite)

    // Cursor
    @Query("SELECT * FROM user_favorite_table")
    fun cursorGetAll(): Cursor
}