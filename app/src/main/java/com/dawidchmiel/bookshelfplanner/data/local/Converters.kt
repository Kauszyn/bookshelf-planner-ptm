package com.dawidchmiel.bookshelfplanner.data.local

import androidx.room.TypeConverter
import com.dawidchmiel.bookshelfplanner.model.BookStatus

class Converters {
    @TypeConverter
    fun fromStatus(status: BookStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): BookStatus = BookStatus.valueOf(value)
}
