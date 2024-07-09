package com.example.bloggie.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloggie.database.Favorite
import com.example.bloggie.use_case.DeleteUseCase
import com.example.bloggie.use_case.FetchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(
    private val loadFavorites: FetchUseCase<List<Favorite>>,
    private val deleteFavorite: DeleteUseCase<Favorite>
) : ViewModel() {

    private val _state = MutableStateFlow<List<Favorite>>(emptyList())
    val state = _state.asStateFlow()
    private val _deletedFavoriteIds = MutableStateFlow<Set<Int>>(emptySet())

    init {
        load()
    }

    fun removeBookmark(p: Favorite) {
        viewModelScope.launch {
            withContext(Dispatchers.IO + viewModelScope.coroutineContext) {
                deleteFavorite(p)
                _deletedFavoriteIds.update {
                    it + p.uid
                }
                _state.update {
                    it.filter { item ->
                        !_deletedFavoriteIds.value.contains(item.uid)
                    }

                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO + viewModelScope.coroutineContext) {
                loadFavorites()
            }
            _state.emit(response)
        }
    }
}