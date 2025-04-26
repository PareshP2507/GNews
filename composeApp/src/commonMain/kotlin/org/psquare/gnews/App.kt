package org.psquare.gnews

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.psquare.gnews.di.datasourceModule
import org.psquare.gnews.di.networkModule
import org.psquare.gnews.di.repositoryModule
import org.psquare.gnews.ui.screen.FeedScreen
import org.psquare.gnews.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(networkModule, datasourceModule, repositoryModule)
    }) {
        AppTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Feed) {
                composable<Feed> { FeedScreen() }
            }
        }
    }
}

@Serializable
object Feed