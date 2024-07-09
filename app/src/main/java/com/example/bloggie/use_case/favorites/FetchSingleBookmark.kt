package com.example.bloggie.use_case.favorites

import com.example.bloggie.database.BloggieDatabase
import com.example.bloggie.database.Favorite
import com.example.bloggie.use_case.FetchSingleUseCase
import javax.inject.Inject

class FetchSingleBookmark
@Inject
constructor(
  val  database: BloggieDatabase
) : FetchSingleUseCase<Favorite>{
    override suspend fun invoke(item: Favorite): Favorite {
        return database.favorites().findById(item.uid)
    }
}