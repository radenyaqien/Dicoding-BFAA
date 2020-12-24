package id.radenyaqien.githubuserdicoding.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.radenyaqien.githubuserdicoding.data.repository.BaseRepository
import id.radenyaqien.githubuserdicoding.data.repository.DetailRepository
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.persistence.FavUserRepository
import id.radenyaqien.githubuserdicoding.persistence.FavoriteViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository as SearchRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository as DetailRepository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                repository as FavUserRepository
            ) as T
            else -> throw IllegalArgumentException("ViewModelClass NOt Found")
        }

    }
}