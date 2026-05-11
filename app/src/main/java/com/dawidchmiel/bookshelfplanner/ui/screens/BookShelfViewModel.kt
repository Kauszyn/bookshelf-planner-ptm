package com.dawidchmiel.bookshelfplanner.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawidchmiel.bookshelfplanner.data.repository.BookRepository
import com.dawidchmiel.bookshelfplanner.model.Book
import com.dawidchmiel.bookshelfplanner.model.BookStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val books: List<Book>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

class BookShelfViewModel(private val repository: BookRepository) : ViewModel() {
    val savedBooks: StateFlow<List<Book>> = repository.savedBooks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchState = _searchState.asStateFlow()

    fun onQueryChange(value: String) { _query.value = value }

    fun search() {
        val currentQuery = query.value.trim()
        if (currentQuery.length < 2) {
            _searchState.value = SearchUiState.Error("Type at least 2 characters.")
            return
        }
        viewModelScope.launch {
            _searchState.value = SearchUiState.Loading
            _searchState.value = runCatching { repository.searchBooks(currentQuery) }
                .fold(
                    onSuccess = { SearchUiState.Success(it) },
                    onFailure = { SearchUiState.Error(it.message ?: "Network error") }
                )
        }
    }

    fun save(book: Book) = viewModelScope.launch { repository.saveBook(book) }
    fun delete(book: Book) = viewModelScope.launch { repository.deleteBook(book) }
    fun updateStatus(book: Book, status: BookStatus) = viewModelScope.launch { repository.updateStatus(book, status) }
    fun updateNote(book: Book, note: String) = viewModelScope.launch { repository.updateNote(book, note) }
}
