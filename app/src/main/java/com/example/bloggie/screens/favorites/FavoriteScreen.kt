package com.example.bloggie.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun FavoriteScreen(controller: NavController,viewModel: FavoriteViewModel = hiltViewModel()){
    val favorites by viewModel.state.collectAsStateWithLifecycle()

    Scaffold (modifier = Modifier.fillMaxSize()){
        when(favorites.isEmpty()){
            true ->Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text("No Favorites yet", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text("Please click on Home to view your favorite blogs",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray))
                }
            }
            else ->LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(favorites) {
                    ListItem(headlineContent = { Text(text = it.title ?: "") },
                        trailingContent = {
                            IconButton(
                                onClick = { viewModel.removeBookmark(it) },
                                colors = IconButtonDefaults.filledIconButtonColors(),
                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = null
                                )
                            }
                        })
                    HorizontalDivider(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}