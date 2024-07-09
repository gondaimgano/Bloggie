package com.example.bloggie.use_case.favorites

import com.example.bloggie.database.BloggieDatabase
import com.example.bloggie.database.Favorite
import com.example.bloggie.use_case.DeleteUseCase
import javax.inject.Inject


class RemoveBookmark
@Inject
    constructor(val database: BloggieDatabase): DeleteUseCase<Favorite> {
    override suspend fun invoke(favorite: Favorite) {
        return database.favorites().deleteById(favorite.uid)
    }
}