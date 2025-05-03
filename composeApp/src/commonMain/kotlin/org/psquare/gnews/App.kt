package org.psquare.gnews

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.psquare.gnews.di.categoryModule
import org.psquare.gnews.di.datasourceModule
import org.psquare.gnews.di.dispatchersModule
import org.psquare.gnews.di.networkModule
import org.psquare.gnews.di.repositoryModule
import org.psquare.gnews.di.viewModelModule
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.ui.screen.detail.ArticleDetailScreen
import org.psquare.gnews.ui.screen.home.HomeScreen
import org.psquare.gnews.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(
            networkModule,
            datasourceModule,
            repositoryModule,
            categoryModule,
            viewModelModule,
            dispatchersModule
        )
    }) {
        AppTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Home) {
                composable<Home> {
                    HomeScreen { article ->
                        navController.navigate(route = article)
                    }
                }
                composable<ArticleEntity> { backstackEntry ->
                    ArticleDetailScreen(backstackEntry.toRoute()) {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}

@Serializable
object Home