package org.psquare.gnews

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.ui.screen.detail.ArticleDetailScreen
import org.psquare.gnews.ui.screen.home.HomeScreen
import org.psquare.gnews.ui.theme.AppTheme

@Composable
@Preview
fun App() {
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

@Serializable
object Home