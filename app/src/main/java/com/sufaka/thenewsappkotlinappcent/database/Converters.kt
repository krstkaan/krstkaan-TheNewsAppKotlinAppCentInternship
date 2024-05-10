package com.sufaka.thenewsappkotlinappcent.database

import androidx.room.TypeConverter
import com.sufaka.thenewsappkotlinappcent.models.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name // name is both id and name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}