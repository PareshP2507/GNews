package org.psquare.gnews.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.BookmarkRepository
import org.psquare.gnews.domain.repository.NewsRepository

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _bookmarkArticles = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val bookmarkArticles = _bookmarkArticles.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.getBookmarkAsFlow()
                .collectLatest {
                    _bookmarkArticles.value = it
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
}