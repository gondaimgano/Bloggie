package com.example.bloggie.screens.list

import com.example.bloggie.model.Post

sealed class PostState {
    data object Loading: PostState()
    data class Success(val response:List<Post>):PostState()
    data class Failed(val message:String? = null): PostState()
}