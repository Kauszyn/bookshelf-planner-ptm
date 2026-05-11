package com.dawidchmiel.bookshelfplanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(viewModel: BookShelfViewModel, bookId: String, onBack: () -> Unit) {
    val books by viewModel.savedBooks.collectAsStateWithLifecycle()
    val book = books.firstOrNull { it.id == bookId }
    var note by remember(book?.personalNote) { mutableStateOf(book?.personalNote.orEmpty()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Book details") }, navigationIcon = { IconButton(onClick = onBack) { Text("‹") } }) }
    ) { padding ->
        if (book == null) {
            Text("Book not found.", modifier = Modifier.padding(padding).padding(16.dp))
            return@Scaffold
        }
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = book.thumbnailUrl,
                    contentDescription = book.title,
                    modifier = Modifier.size(width = 112.dp, height = 168.dp).clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(start = 16.dp).weight(1f)) {
                    Text(book.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(book.authors, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Published: ${book.publishedDate ?: "unknown"}")
                    Text("Pages: ${book.pageCount ?: 0}")
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Reading status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            StatusSelector(current = book.status, onStatusSelected = { viewModel.updateStatus(book, it) })
            Spacer(Modifier.height(20.dp))
            Text("Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(book.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Personal note") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            Button(onClick = { viewModel.updateNote(book, note) }) { Text("Save note") }
        }
    }
}
