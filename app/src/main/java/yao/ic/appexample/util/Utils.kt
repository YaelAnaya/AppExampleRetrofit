package yao.ic.appexample.util

import androidx.core.text.HtmlCompat
import yao.ic.appexample.data.model.Show

const val FAVORITE_SHOWS_TABLE = "favorite_shows"
const val DATABASE_NAME = "tv_maze_database"

fun Show.getDescription(): String {
    return HtmlCompat.fromHtml(this.description ?: " ", HtmlCompat.FROM_HTML_MODE_COMPACT)
        .toString()
}

fun Show.getGenres(): String {
    return this.genres.ifEmpty { listOf("No genre") }.joinToString(separator = ", ")
}