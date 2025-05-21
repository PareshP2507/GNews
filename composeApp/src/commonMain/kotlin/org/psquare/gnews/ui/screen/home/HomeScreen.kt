package org.psquare.gnews.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gnews.composeapp.generated.resources.Res
import gnews.composeapp.generated.resources.ic_bookmark_filled
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.psquare.gnews.data.repository.category.Category
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.ui.screen.common.ArticleListItem
import org.psquare.gnews.ui.screen.common.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleClick: (ArticleEntity) -> Unit,
    onBookmarkClick: () -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollingBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HomeAppbar(
                scrollingBehavior = scrollingBehavior,
                onBookmarkClick = onBookmarkClick
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier.nestedScroll(scrollingBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            viewModel,
            onArticleClick,
            onRefresh = {
                viewModel.refreshArticles()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppbar(
    modifier: Modifier = Modifier,
    scrollingBehavior: TopAppBarScrollBehavior,
    onBookmarkClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "Top Headlines", style = MaterialTheme.typography.headlineMedium) },
        scrollBehavior = scrollingBehavior,
        actions = {
            IconButton(onClick = { onBookmarkClick.invoke() }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_bookmark_filled),
                    contentDescription = "Bookmarks"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onArticleClick: (ArticleEntity) -> Unit,
    onRefresh: () -> Unit
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val lastUpdatedText by viewModel.lastUpdatedText.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.categories) { category ->
                CategoryTab(category, category == selectedCategory) {
                    viewModel.onCategorySelected(category)
                }
            }
        }
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh
        ) {
            val articles = uiState.articles
            if (articles.isEmpty()) {
                EmptyState()
            } else {
                Column {
                    Text(
                        text = lastUpdatedText,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.End
                    )
                    FeedList(
                        articles = articles,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = { article ->
                            viewModel.onBookmarkClick(article)
                        })
                }
            }
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

@Composable
private fun CategoryTab(category: Category, isSelected: Boolean, onClick: (Category) -> Unit) {
    val bgColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.background
    }
    val shape = RoundedCornerShape(8.dp)
    Text(
        text = category.name(),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.clip(shape)
            .clickable(onClick = { onClick.invoke(category) })
            .background(color = bgColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
