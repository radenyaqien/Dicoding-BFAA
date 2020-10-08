package id.radenyaqien.githubuserdicoding.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.radenyaqien.githubuserdicoding.data.repository.BaseRepository
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository


class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository as SearchRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass NOt Found")
        }

    }
}