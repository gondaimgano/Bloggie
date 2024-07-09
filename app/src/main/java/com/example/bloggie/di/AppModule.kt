package com.example.bloggie.di

import android.content.Context
import androidx.room.Room
import com.example.bloggie.BuildConfig
import com.example.bloggie.database.BloggieDatabase
import com.example.bloggie.database.Favorite
import com.example.bloggie.model.Post
import com.example.bloggie.network.BloggieService
import com.example.bloggie.use_case.DeleteUseCase
import com.example.bloggie.use_case.FetchSingleUseCase
import com.example.bloggie.use_case.FetchUseCase
import com.example.bloggie.use_case.SaveUseCase
import com.example.bloggie.use_case.favorites.FetchBookmarks
import com.example.bloggie.use_case.favorites.FetchSingleBookmark
import com.example.bloggie.use_case.favorites.RemoveBookmark
import com.example.bloggie.use_case.favorites.SaveBookmark
import com.example.bloggie.use_case.post.FetchPosts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesService() : BloggieService = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BloggieService::class.java)


    @Provides
    fun providePostUseCase(service: BloggieService): FetchUseCase<List<Post>> = FetchPosts(service)


    @Provides
    fun provideBookmarkUseCase(database: BloggieDatabase): FetchUseCase<List<Favorite>> = FetchBookmarks(database)

    @Provides
    fun provideBookmarkSingleUseCase(database: BloggieDatabase): FetchSingleUseCase<Favorite> = FetchSingleBookmark(database)

    @Provides
    fun provideSaveToFavorite(database: BloggieDatabase): SaveUseCase = SaveBookmark(database)


    @Provides
    fun provideDeleteFavorite(database: BloggieDatabase): DeleteUseCase<Favorite> = RemoveBookmark(database)

    @Provides
    @Singleton
    fun provideDatabaseCache(
        @ApplicationContext
        context: Context):BloggieDatabase{
  return Room.databaseBuilder(
      context, BloggieDatabase::class.java, "bloggie-db").build()
    }

}