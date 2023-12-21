package yao.ic.appexample.data.model

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.core.text.HtmlCompat
import com.squareup.moshi.Json
import yao.ic.appexample.data.database.entity.FavoriteShow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


data class Show(
    val id: Int = 0,
    val name: String = "",
    val genres: List<String> = emptyList(),
    val rating: Rating = Rating(),
    val image: Image? = Image(),
    @Json(name = "summary") val description: String? = "",
    val network : Network? = Network(),
    val premiered: String = "",
    val language: String = "",
    var isFavorite: Boolean? = false
) {
    fun toFormattedDescription(): String {
        return HtmlCompat.fromHtml(this.description ?: " ", HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()
    }

    fun toGenresString(): String {
        return this.genres.ifEmpty { listOf("No genre") }.joinToString(separator = ", ")
    }
    fun toPremieredString(): String {
        return premiered.ifEmpty { "2009-05-19" }.let {
            val date = LocalDate.parse(it)
             date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
        }
    }

    fun toFavoriteShow(): FavoriteShow {
        return FavoriteShow(
            id = this.id,
            name = this.name,
            genres = this.toGenresString(),
            image = this.image?.medium ?: "",
            rate = this.rating.average.toString(),
        )
    }
}


data class ShowSearch(
    val score: Double? = 0.0,
    val show: Show = Show()
) {
    fun toShow() = show
}

data class Rating(
    val average: Double? = 0.0
)

data class Image(
    val medium: String = "",
    val original: String = ""

)

data class Network(
    val id: Int = 0,
    val name: String? = "",
    val country: Country? = Country()
)

data class Country(
    val name: String = "",
    val code: String = "",
    val timezone: String = ""
) {
    override fun toString(): String {
        return "$name, $code"
    }
}