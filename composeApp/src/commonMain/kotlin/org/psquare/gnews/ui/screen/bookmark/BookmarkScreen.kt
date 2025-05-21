package org.psquare.gnews.ui.screen.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.ui.screen.common.ArticleListItem
import org.psquare.gnews.ui.screen.common.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    onArticleClick: (ArticleEntity) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: BookmarkViewModel = koinViewModel<BookmarkViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollingBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            BookmarkTopAppbar(
                scrollingBehavior = scrollingBehavior,
                onCloseClick = onBackClick
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier.nestedScroll(scrollingBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            viewModel,
            onArticleClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookmarkTopAppbar(
    modifier: Modifier = Modifier,
    scrollingBehavior: TopAppBarScrollBehavior,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Bookmarks", style = MaterialTheme.typography.titleLarge)
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(onClick = { onCloseClick.invoke() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        scrollBehavior = scrollingBehavior
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel,
    onArticleClick: (ArticleEntity) -> Unit
) {
    val articles by viewModel.bookmarkArticles.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        if (articles.isEmpty()) {
            EmptyState()
        } else {
            FeedList(
                articles = articles,
                onArticleClick = onArticleClick,
                onBookmarkClick = { article ->
                    viewModel.onBookmarkClick(article)
                })
        }
    }
}

@Composable
private fun FeedList(
    modifier: Modifier = Modifier,
    articles: List<ArticleEntity>,
    onArticleClick: (ArticleEntity) -> Unit,
    onBookmarkClick: (ArticleEntity) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(0.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) {
        items(articles) { article ->
            ArticleListItem(articleEntity = article, onArticleClick, onBookmarkClick)
        }
    }
}
