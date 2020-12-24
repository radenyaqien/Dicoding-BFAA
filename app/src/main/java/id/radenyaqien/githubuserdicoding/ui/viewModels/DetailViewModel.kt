package id.radenyaqien.githubuserdicoding.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import id.radenyaqien.githubuserdicoding.data.models.DetailUserResponse
import id.radenyaqien.githubuserdicoding.data.repository.DetailRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.persistence.UserFavorite
import kotlinx.coroutines.launch

class DetailViewModel
@ViewModelInject
constructor(
    private val repository: DetailRepository
) : ViewModel() {


    fun getDetailUser(username: String) = viewModelScope.launch {
        _detailResponse.value = repository.GetDetailUser(username)
    }

    private val _detailResponse: MutableLiveData<Resource<DetailUserResponse>> = MutableLiveData()
    val detailResponse: LiveData<Resource<DetailUserResponse>>
        get() = _detailResponse


    /**
     * insert into DB
     */
    private val _resultInsertUserToDb = MutableLiveData<Boolean>()
    val resultInsertUserDb: LiveData<Boolean>
        get() = _resultInsertUserToDb

    fun insertToDB(userFavorite: UserFavorite) {
        viewModelScope.launch {
            try {
                repository.insert(userFavorite)
                _resultInsertUserToDb.value = true
            } catch (e: Exception) {
                _resultInsertUserToDb.value = false
            }

        }
    }


    /**
     * User Detail from DB
     */
//    private var _resultUserDetailFromDb = MutableLiveData<List<UserFavorite>>()
//    val resultUserDetailFromDb : LiveData<List<UserFavorite>>
//        get() = _resultUserDetailFromDb
    fun getFavUserByUsername(username: String) =
        repository.getFavoriteUserByUsername(username).asLiveData()

    private val _resultdeleteUserToDb = MutableLiveData<Boolean>()
    val resultDeleteUserDb: LiveData<Boolean>
        get() = _resultInsertUserToDb

    fun deleteUserFromDb(userFavorite: UserFavorite) {
        viewModelScope.launch {
            try {
                repository.delete(userFavorite)
                _resultdeleteUserToDb.value = true
            } catch (e: Exception) {
                _resultdeleteUserToDb.value = false
            }

        }
    }
}