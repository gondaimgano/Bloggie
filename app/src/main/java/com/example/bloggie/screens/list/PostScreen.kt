package com.example.bloggie.screens.list


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bloggie.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(controller: NavController, viewModel: PostListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text("Bloggie")
            }, actions = {
                IconButton(onClick = { viewModel.fetchAll() }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null
                    )
                }
            })
        }) { paddingValues ->
        when (state) {
            is PostState.Failed -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(text = "Something went wrong :(")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = { viewModel.fetchAll() }) {
                        Text("Try again")
                    }
                }
            }

           is PostState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is PostState.Success ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    (state as? PostState.Success)?.let {
                        items(it.response) { item ->
                            Spacer(modifier = Modifier.padding(2.dp))
                            PostClick(item = item, favoriteIds.contains(item.id)) {
                                viewModel.bookmark(it)
                            }
                            HorizontalDivider()
                        }
                    }
                }
        }

    }
}

@Composable
private fun PostClick(item: Post, isBookmarked: Boolean = false, onClick: (Post) -> Unit) {

    ListItem(
        headlineContent = { Text(item.title ?: "") },
        supportingContent = { Text(item.body ?: "") },
        trailingContent = {
            IconButton(
                onClick = { onClick(item) },
                colors =  if (isBookmarked) IconButtonDefaults.filledIconButtonColors() else IconButtonDefaults.outlinedIconButtonColors(),
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null
                )

            }
        }
    )
}