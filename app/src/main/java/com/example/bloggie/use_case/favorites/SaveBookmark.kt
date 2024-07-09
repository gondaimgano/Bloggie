package com.example.bloggie.use_case.favorites


import com.example.bloggie.database.BloggieDatabase
import com.example.bloggie.database.Favorite
import com.example.bloggie.model.Post
import com.example.bloggie.use_case.SaveUseCase
import javax.inject.Inject

class SaveBookmark
@Inject
    constructor(val database: BloggieDatabase): SaveUseCase {
    override suspend fun invoke(id: Post): Boolean {
       try {
           database.favorites().save(Favorite(
               uid = id.id?:0,
               title = id.title,
               body = id.body
           ))
       } catch (ex:Exception){
           return false
       }
        return true
    }
}