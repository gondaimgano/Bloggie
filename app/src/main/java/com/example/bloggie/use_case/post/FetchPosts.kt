package com.example.bloggie.use_case.post

import com.example.bloggie.model.Post
import com.example.bloggie.network.BloggieService
import com.example.bloggie.use_case.FetchUseCase
import javax.inject.Inject

class FetchPosts
@Inject
    constructor(val service: BloggieService):
    FetchUseCase<List<Post>> {
    override suspend fun invoke(): List<Post> {
       return service.fetchPosts()
    }
}