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

class HomeViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun initWithCategories(categoryList: List<Category>) {
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
            val articles = newsRepository.getArticles(category.urlParamName())
            _homeUiState.update { homeUiState -> homeUiState.copy(articles = articles) }
        }
    }

    fun onCategorySelected(category: Category) {
        _homeUiState.update { homeUiState ->
            homeUiState.copy(selectedCategory = category)
        }
        retrieveArticlesFor(category)
    }

    data class HomeUiState(
        val categories: List<Category> = emptyList(),
        val selectedCategory: Category? = null,
        val articles: List<ArticleEntity> = emptyList()
    )
}