package id.radenyaqien.githubuserdicoding.persistence

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class FavoriteViewModel @ViewModelInject constructor(private val repository: FavUserRepository) :
    ViewModel() {


    val _favUser = repository.getALl().asLiveData()


}