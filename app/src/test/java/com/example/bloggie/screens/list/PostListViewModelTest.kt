package com.example.bloggie.screens.list

import com.example.bloggie.database.Favorite
import com.example.bloggie.model.Post
import com.example.bloggie.use_case.FetchUseCase
import com.example.bloggie.use_case.SaveUseCase
import com.example.bloggie.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class PostListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchListOfPost: FetchUseCase<List<Post>> = mock()
    private val fetchFavorites: FetchUseCase<List<Favorite>> = mock()
    private val saveToFavorites: SaveUseCase = mock()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `fetchAll emits Success state when data is fetched successfully`() = runTest {
        // Given
        val posts = listOf(Post(1, 1, "Title", "Body"))
        val favorites = listOf(Favorite(1, "Title", "Body"))
        `when`(fetchListOfPost()).thenReturn(posts)
        `when`(fetchFavorites()).thenReturn(favorites)

        // When
        val viewModel = PostListViewModel(fetchListOfPost, fetchFavorites, saveToFavorites, testDispatcher)

        // Then
        val currentState = viewModel.state.value
        assertTrue(currentState is PostState.Success)
        assertEquals(posts, (currentState as PostState.Success).response)
        assertEquals(setOf(1), viewModel.favoriteIds.value)
    }

    @Test
    fun `fetchAll emits Failed state when fetching fails`() = runTest {
        // Given
        `when`(fetchListOfPost()).thenThrow(RuntimeException("Network error"))

        // When
        val viewModel = PostListViewModel(fetchListOfPost, fetchFavorites, saveToFavorites, testDispatcher)

        // Then
        val currentState = viewModel.state.value
        assertTrue(currentState is PostState.Failed)
        assertEquals("Network error", (currentState as PostState.Failed).message)
    }

    @Test
    fun `bookmark saves post and updates favoriteIds`() = runTest {
        // Given
        val post = Post(1, 1, "Title", "Body")
        `when`(fetchListOfPost()).thenReturn(emptyList())
        `when`(fetchFavorites()).thenReturn(emptyList())
        `when`(saveToFavorites(any())).thenReturn(true)

        val viewModel = PostListViewModel(fetchListOfPost, fetchFavorites, saveToFavorites, testDispatcher)

        // When
        viewModel.bookmark(post)

        // Then
        verify(saveToFavorites).invoke(post)
        assertTrue(viewModel.favoriteIds.value.contains(1))
    }
}
