package id.radenyaqien.githubuserdicoding.data.repository

import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface
import id.radenyaqien.githubuserdicoding.persistence.UserDao
import id.radenyaqien.githubuserdicoding.persistence.UserFavorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: GithubInterface,
    private val userDao: UserDao
) : BaseRepository() {


    suspend fun GetDetailUser(
        username: String
    ) = safeApiCall {
        api.getDetailUser(username)
    }

    suspend fun delete(user: UserFavorite) {
        userDao.delete(user)
    }

    suspend fun insert(user: UserFavorite) {
        userDao.add(user)
    }

    fun fetchAllUserFavorite(): Flow<List<UserFavorite>> {
        return userDao.getAll()
    }

    fun getFavoriteUserByUsername(username: String): Flow<List<UserFavorite>> {
        return userDao.getByUserName(username)
    }
}




