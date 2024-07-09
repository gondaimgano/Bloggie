package com.example.bloggie.index

import androidx.annotation.DrawableRes
import com.example.bloggie.R
import kotlinx.serialization.Serializable

@Serializable
open class Index(val route:String,
                 @DrawableRes
    val icon:Int) {
    @Serializable
    class PostPage: Index("Post", R.drawable.baseline_home_24)
    @Serializable
    class FavoritePage: Index("Favorite", R.drawable.baseline_favorite_24)
}