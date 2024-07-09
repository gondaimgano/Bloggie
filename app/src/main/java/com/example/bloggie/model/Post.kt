package com.example.bloggie.model

import com.example.bloggie.database.Favorite

//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.bloggie.database.Favorite


class Post(
    var userId: Int? = null,
    var id: Int? = null,
    var title: String? = null,
    var body: String? = null
)


fun Post.toFavorite(): Favorite {
    return Favorite(
        this.id?:0,
        title?:"",
        body?:""
    )
}
