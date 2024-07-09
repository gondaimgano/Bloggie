package com.example.bloggie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloggie.index.Index
import com.example.bloggie.screens.favorites.FavoriteScreen
import com.example.bloggie.screens.list.PostScreen
import com.example.bloggie.ui.theme.BloggieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloggieTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(name: String, modifier: Modifier = Modifier) {
    val controller = rememberNavController()
    val navs = listOf(Index.PostPage(),Index.FavoritePage())
    var selectedItem by remember {
        mutableStateOf(navs[0])
    }
   Scaffold(
       bottomBar = {
           NavigationBar {
               navs.forEach {
                   NavigationBarItem(selected = selectedItem.route==it.route, onClick = {
                           controller.navigate(it){
                               launchSingleTop = true
                               restoreState = true
                           }
                       selectedItem = it
                   },
                       icon = { Icon(painter = painterResource(id = it.icon), contentDescription = null) })
               }
           }
       }
   ) {
       NavHost(navController = controller, startDestination = Index.PostPage(), modifier = Modifier.padding(it)) {
           composable<Index.PostPage>{
               PostScreen(controller)
           }
           composable<Index.FavoritePage> {
               FavoriteScreen(controller)
           }
       }
   }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BloggieTheme {
        MyApp("Android")
    }
}