package com.example.bloggie.network

import com.example.bloggie.model.Post
import retrofit2.http.GET


interface BloggieService {
    @GET("posts")
    suspend fun fetchPosts(): List<Post>
}