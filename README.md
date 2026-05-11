# BookShelf Planner

Simple Android application for keeping a personal reading list.
Search books on Google Books, save the ones you are interested in,
mark their reading status and add your own notes.

The app is written in Kotlin with Jetpack Compose and Room.
This is the first version of the project, more features will be added later.

## Features

- Search books by title, author or topic (Google Books API).
- Save selected books to a local list.
- Local persistence with Room.
- Three reading statuses: *To read*, *Reading*, *Finished*.
- Filter saved books by status.
- Add and edit a personal note for each book.
- Delete books from the list.
- Separate screens for Home, Search and Book details (Navigation Compose).
- Handles loading, empty and error states.

## Tech stack

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- ViewModel + Kotlin Coroutines / Flow
- Room (local database)
- Retrofit + Moshi (Google Books API)
- Coil (image loading)

## Project structure

```
app/src/main/java/com/dawidchmiel/bookshelfplanner/
├── MainActivity.kt
├── BookShelfApplication.kt
├── AppContainer.kt
├── model/                  # Book, BookStatus
├── data/
│   ├── local/              # Room: BookEntity, BookDao, BookDatabase, Converters
│   ├── remote/             # Retrofit: GoogleBooksApi, DTOs
│   └── repository/         # BookRepository
└── ui/
    ├── screens/            # HomeScreen, SearchScreen, BookDetailScreen, ViewModel
    └── theme/              # BookShelfTheme
```

## Requirements

- Android Studio Ladybug or newer
- Android SDK 35
- JDK 17
- A device or emulator with Android 8.0 (API 26) or higher

## How to run

1. Clone the repository:
   ```bash
   git clone https://github.com/Kauszyn/bookshelf-planner-ptm.git
   ```
2. Open the project folder in Android Studio (open the root `bookshelf-planner` folder, not the `app` folder).
3. Wait for the Gradle sync to finish.
4. Pick an emulator or connect a device and press **Run**.

The Google Books API does not require an API key for basic search,
so no extra configuration is needed.

## Manual test scenario

1. Start the app.
2. Open **Search** and type for example `Kotlin` or `Clean Code`.
3. Add a few books to your list.
4. Go back to **Home**.
5. Open one of the saved books, change its status and save a note.
6. Close and reopen the app — the data should still be there.

## Planned improvements

- Sorting on the Home screen (title, status, page count).
- Search input debouncing.
- Snackbar confirmation after saving a book.
- Reading progress field (current page).
- Unit tests for the repository.
- Better empty-state graphics.

## License

This project is released under the MIT License. See [LICENSE](LICENSE) for details.
