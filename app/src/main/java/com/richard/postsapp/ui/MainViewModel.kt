package com.richard.postsapp.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.richard.postsapp.adapter.PostAdapter
import com.richard.postsapp.data.Post
import com.richard.postsapp.repository.MainRepository
import com.richard.postsapp.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Post>>> = MutableLiveData()
    private var postAdapter: PostAdapter? = null

    val dataState: LiveData<DataState<List<Post>>>
        get() = _dataState

    private val _currentPosts : MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>()
    }

    val currentPosts: LiveData<List<Post>>
        get() = _currentPosts

    fun getPosts() {
        viewModelScope.launch {
            _currentPosts.value = mainRepository.getAllPosts()
        }
    }

    fun addNewPost(post: Post) {
        viewModelScope.launch {
            val newPost = mainRepository.insertPost(post)
            if (newPost?.id != null) {
                Log.i("MAIN_VIEW_MODEL", "addNewPost: newPost $newPost")
                Log.i("MAIN_VIEW_MODEL", "addNewPost: all posts ${_currentPosts.value}")
            } else {
                Log.i("MAIN_VIEW_MODEL", "addNewPost: error")
            }
        }
    }

    fun setStateEvent(mainStateEvent: MainStateEvent, post: Post?) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetPostEvents -> {
                    mainRepository.getPost()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.AddPostEvents -> {
                    post?.let {
                        Log.i("MAIN_VIEW_MODEL", "setStateEvent: newPost $post")
                        mainRepository.insertPost(it)
                    }
                }
                is MainStateEvent.None -> {
                    // who cares
                }
            }
        }
    }
}

sealed class MainStateEvent {

    object GetPostEvents: MainStateEvent()

    object AddPostEvents: MainStateEvent()

    object None: MainStateEvent()
}