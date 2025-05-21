package org.psquare.gnews.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.repository.category.Category
import org.psquare.gnews.data.repository.refresh.CategoryRefreshRepository
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository

class HomeViewModel(
    private val newsRepository: NewsRepository,
    private val categoryRefreshRepository: CategoryRefreshRepository,
    private val dateConverter: DateConverter
) : ViewModel(), KoinComponent {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val lastRefreshTime = _selectedCategory.filterNotNull().flatMapLatest {
        categoryRefreshRepository.getLastRefreshAsFlow(it.urlParamName())
    }

    private val ticker = flow {
        while (true) {
            emit(Unit)
            kotlinx.coroutines.delay(30_000L)
        }
    }

    val lastUpdatedText = combine(lastRefreshTime, ticker) { lastRefreshTime, _ ->
        lastRefreshTime?.let { time ->
            val now = Clock.System.now()
            val diff = now.minus(time)
            "Last updated: ${dateConverter.formatDuration(diff)}"
        } ?: "No recent update"
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Loading..."
    )

    init {
        val categories = getKoin().getAll<Category>().sortedBy { it.name() }
        initWithCategories(categories)
    }

    private fun initWithCategories(categoryList: List<Category>) {
        _homeUiState.update { homeUiState -> homeUiState.copy(categories = categoryList) }
        selectFirstCategory()
    }

    private fun selectFirstCategory() {
        _homeUiState.value.categories.getOrNull(index = 0)?.let { category ->
            onCategorySelected(category)
        }
    }

    private fun retrieveArticlesFor(category: Category) {
        viewModelScope.launch {
            _homeUiState.update { homeUiState ->
                homeUiState.copy(isRefreshing = true, articles = emptyList())
            }
            newsRepository.getArticlesAsFlow(category.urlParamName())
                .collectLatest { articles ->
                    if (category == _selectedCategory.value) {
                        if (articles.isEmpty()) {
                            newsRepository.refreshArticles(category.urlParamName())
                        } else {
                            _homeUiState.update { homeUiState ->
                                homeUiState.copy(isRefreshing = false, articles = articles)
                            }
                        }
                    }
                }
        }
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
        retrieveArticlesFor(category)
    }

    fun refreshArticles() {
        _selectedCategory.value?.urlParamName()?.let { urlParam ->
            viewModelScope.launch {
                _homeUiState.update { homeUiState -> homeUiState.copy(isRefreshing = true) }
                newsRepository.refreshArticles(urlParam)
                _homeUiState.update { homeUiState -> homeUiState.copy(isRefreshing = false) }
            }
        }
    }

    fun onBookmarkClick(articleEntity: ArticleEntity) {
        if (articleEntity.isBookmarked) {
            viewModelScope.launch { newsRepository.removeBookmark(id = articleEntity.id) }
        } else {
            viewModelScope.launch { newsRepository.addBookmark(id = articleEntity.id) }
        }
    }

    data class HomeUiState(
        val categories: List<Category> = emptyList(),
        val isRefreshing: Boolean = false,
        val articles: List<ArticleEntity> = emptyList()
    )
}