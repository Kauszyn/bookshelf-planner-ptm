package com.dawidchmiel.bookshelfplanner

import android.app.Application

class BookShelfApplication : Application() {
    val appContainer: AppContainer by lazy { AppContainer(this) }
}
