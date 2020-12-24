package id.radenyaqien.githubuserdicoding.persistence

import id.radenyaqien.githubuserdicoding.data.repository.BaseRepository
import javax.inject.Inject

class FavUserRepository @Inject constructor(private val userDAO: UserDao) : BaseRepository() {

    fun getALl() = userDAO.getAll()

}