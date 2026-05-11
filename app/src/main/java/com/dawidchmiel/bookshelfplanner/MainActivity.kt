package com.dawidchmiel.bookshelfplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dawidchmiel.bookshelfplanner.ui.screens.BookDetailScreen
import com.dawidchmiel.bookshelfplanner.ui.screens.BookShelfViewModel
import com.dawidchmiel.bookshelfplanner.ui.screens.HomeScreen
import com.dawidchmiel.bookshelfplanner.ui.screens.SearchScreen
import com.dawidchmiel.bookshelfplanner.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {
    private val viewModel: BookShelfViewModel by viewModels {
        val app = application as BookShelfApplication
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BookShelfViewModel(app.appContainer.repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookShelfTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BookShelfApp(viewModel)
                }
            }
        }
    }
}

@Composable
private fun BookShelfApp(viewModel: BookShelfViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onSearchClick = { navController.navigate("search") },
                onBookClick = { navController.navigate("detail/$it") }
            )
        }
        composable("search") {
            SearchScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { entry ->
            BookDetailScreen(
                viewModel = viewModel,
                bookId = entry.arguments?.getString("bookId").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
