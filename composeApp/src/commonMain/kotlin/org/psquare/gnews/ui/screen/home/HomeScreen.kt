package org.psquare.gnews.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.viewmodel.koinViewModel
import org.psquare.gnews.data.repository.category.Category
import org.psquare.gnews.domain.entities.ArticleEntity

@Composable
fun HomeScreen(
    onArticleClick: (ArticleEntity) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = { HomeAppbar() },
        snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            viewModel,
            onArticleClick,
            onRefreshClick = {
                viewModel.refreshArticles()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppbar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "Top Headlines", style = MaterialTheme.typography.headlineMedium) }
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onArticleClick: (ArticleEntity) -> Unit,
    onRefreshClick: () -> Unit
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
        if (uiState.isFeedLoading) {
            Loader()
        } else {
            val articles = uiState.articles
            if (articles.isEmpty()) {
                EmptyState()
            } else {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = lastUpdatedText,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Box {
                            if (uiState.isRefreshing) {
                                Loader(modifier = Modifier.size(24.dp))
                            } else {
                                IconButton(onClick = { onRefreshClick.invoke() }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                                }
                            }
                        }
                    }
                    FeedList(articles = articles, onArticleClick = onArticleClick)
                }
            }
        }
    }
}

@Composable
fun Loader(modifier: Modifier = Modifier.size(40.dp)) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No data available.", style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun FeedList(
    modifier: Modifier = Modifier,
    articles: List<ArticleEntity>,
    onArticleClick: (ArticleEntity) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(0.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) {
        items(articles) { article ->
            Article(articleEntity = article) { articleEntity -> onArticleClick.invoke(articleEntity) }
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

@Composable
private fun Article(
    articleEntity: ArticleEntity,
    onClick: (ArticleEntity) -> Unit
) {
    Row(modifier = Modifier.clickable { onClick.invoke(articleEntity) }) {
        Box(
            modifier = Modifier.padding(16.dp)
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            KamelImage(
                { asyncPainterResource(articleEntity.image) },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress ->
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        progress = { progress }
                    )
                }
            )
        }
        Column(modifier = Modifier.weight(1f).padding(top = 16.dp, bottom = 16.dp, end = 16.dp)) {
            Text(
                text = articleEntity.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "${articleEntity.sourceName} · ${articleEntity.elapsedTime}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
