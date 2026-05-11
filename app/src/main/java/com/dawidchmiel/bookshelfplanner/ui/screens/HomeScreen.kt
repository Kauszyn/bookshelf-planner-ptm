package com.dawidchmiel.bookshelfplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dawidchmiel.bookshelfplanner.model.BookStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookShelfViewModel,
    onSearchClick: () -> Unit,
    onBookClick: (String) -> Unit
) {
    val books by viewModel.savedBooks.collectAsState()
    var selectedStatus by remember { mutableStateOf<BookStatus?>(null) }
    val visibleBooks = selectedStatus?.let { status -> books.filter { it.status == status } } ?: books

    Scaffold(
        topBar = { TopAppBar(title = { Text("BookShelf Planner") }) }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text("Your reading plan", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("${books.size} saved books")
                }
                Button(onClick = onSearchClick) { Text("Search") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 16.dp)) {
                FilterChip(selected = selectedStatus == null, onClick = { selectedStatus = null }, label = { Text("All") })
                BookStatus.entries.forEach { status ->
                    FilterChip(selected = selectedStatus == status, onClick = { selectedStatus = status }, label = { Text(status.label) })
                }
            }

            if (visibleBooks.isEmpty()) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = if (books.isEmpty()) "No books yet. Search Google Books and add your first item." else "No books in this filter.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.weight(1f))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(visibleBooks, key = { it.id }) { book ->
                        BookCard(
                            book = book,
                            actionText = "Remove",
                            onAction = { viewModel.delete(book) },
                            onClick = { onBookClick(book.id) }
                        )
                    }
                }
            }
        }
    }
}
