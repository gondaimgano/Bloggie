package com.example.bloggie.use_case.favorites

import com.example.bloggie.database.BloggieDatabase
import com.example.bloggie.database.Favorite
import com.example.bloggie.use_case.FetchUseCase
import javax.inject.Inject

class FetchBookmarks
@Inject
constructor(val database: BloggieDatabase):
    FetchUseCase<List<Favorite>> {
    override suspend fun invoke(): List<Favorite> {
        return database.favorites().getAll()
    }
}