package id.radenyaqien.githubuserdicoding.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.radenyaqien.githubuserdicoding.data.models.FollowResponse
import id.radenyaqien.githubuserdicoding.data.models.SearchResponse
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject
constructor(private val repository: SearchRepository) : ViewModel() {

    private val _searchResponse: MutableLiveData<Resource<SearchResponse>> = MutableLiveData()
    val searchResponse: LiveData<Resource<SearchResponse>>
        get() = _searchResponse

    fun searchUser(username: String) = viewModelScope.launch {
        _searchResponse.value = repository.SearchUser(username)
    }

    private val _followerResponse: MutableLiveData<Resource<List<FollowResponse>>> =
        MutableLiveData()
    val followerResponse: LiveData<Resource<List<FollowResponse>>>
        get() = _followerResponse

    fun getFollower(username: String) = viewModelScope.launch {
        _followerResponse.value = repository.GetFollower(username)
    }

    private val _followingResponse: MutableLiveData<Resource<List<FollowResponse>>> =
        MutableLiveData()
    val followingResponse: LiveData<Resource<List<FollowResponse>>>
        get() = _followingResponse

    fun getFollowing(username: String) = viewModelScope.launch {
        _followingResponse.value = repository.GetFollowing(username)
    }








}