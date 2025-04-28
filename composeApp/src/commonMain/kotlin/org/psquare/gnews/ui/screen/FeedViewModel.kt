package org.psquare.gnews.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.psquare.gnews.data.repository.category.Category
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository

class FeedViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState = _feedUiState.asStateFlow()

    fun initWithCategories(categoryList: List<Category>) {
        _feedUiState.update { feedUiState -> feedUiState.copy(categories = categoryList) }
        selectFirstCategory()
    }

    private fun selectFirstCategory() {
        _feedUiState.value.categories.getOrNull(index = 0)?.let { category ->
            onCategorySelected(category)
        }
    }

    private fun retrieveArticlesFor(category: Category) {
        viewModelScope.launch {
            val articles = newsRepository.getArticles(category.urlParamName())
            _feedUiState.update { feedUiState -> feedUiState.copy(articles = articles) }
        }
    }

    fun onCategorySelected(category: Category) {
        _feedUiState.update { feedUiState ->
            feedUiState.copy(selectedCategory = category)
        }
        retrieveArticlesFor(category)
    }

    data class FeedUiState(
        val categories: List<Category> = emptyList(),
        val selectedCategory: Category? = null,
        val articles: List<ArticleEntity> = emptyList()
    )
}