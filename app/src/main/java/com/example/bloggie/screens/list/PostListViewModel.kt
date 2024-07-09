package com.example.bloggie.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloggie.database.Favorite
import com.example.bloggie.model.Post
import com.example.bloggie.use_case.FetchUseCase
import com.example.bloggie.use_case.SaveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostListViewModel
@Inject
    constructor(
    private val fetchListOfPost: FetchUseCase<List<Post>>,
    private val fetchFavorites: FetchUseCase<List<Favorite>>,
    private val saveToFavorites: SaveUseCase
): ViewModel() {

   private var _state = MutableStateFlow<PostState>(PostState.Loading)
    val state = _state.asStateFlow()
   private var _favoritesIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds = _favoritesIds.asStateFlow()


    init {
        fetchAll()
    }

    fun bookmark(p:Post){
        viewModelScope.launch {
            withContext(Dispatchers.IO+viewModelScope.coroutineContext){
              if(saveToFavorites(p)) {
                  _favoritesIds.update { it + (p.id ?: 0) }
              }
            }
        }
    }

      fun fetchAll() {
         viewModelScope.launch {
            with(_state) {
                emit(PostState.Loading)
                 try {
                     val (response,favorites) = withContext(Dispatchers.IO+viewModelScope.coroutineContext) {
                         fetchListOfPost()  to fetchFavorites()
                     }

                     _favoritesIds.emit(favorites.mapTo(mutableSetOf()){ it.uid })
                     emit(PostState.Success(response))

                 } catch (ex: Exception) {
                    emit(PostState.Failed(ex.message))
                 }
             }
         }
     }
}