package org.psquare.gnews.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FeedScreen() {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = { FeedAppbar() },
        snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        Content(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedAppbar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "Top Headlines", style = MaterialTheme.typography.headlineLarge) },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier
) {

}