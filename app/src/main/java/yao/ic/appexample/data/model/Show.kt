package yao.ic.appexample.data.model

import androidx.core.text.HtmlCompat
import com.squareup.moshi.Json

data class Show(
    val id: Int = 0,
    val name: String = "",
    val genres: List<String> = emptyList(),
    val status: String = "",
    val rating: Rating = Rating(),
    val image: Image? = Image(),
    @Json(name = "summary") val description: String? = "",
    val network : Network? = Network(),
    val premiered: String = "",
    val language: String = "",
    @Json(name = "_embedded") val embedded: Embedded? = Embedded()
)
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

data class Embedded(
    val cast: List<Cast> = emptyList()
)

data class Cast(
    val person: Person = Person(),
    val character: Character = Character()
)

data class Person(
    val id: Int = 0,
    val name: String = "",
    val image: Image? = Image()
)

data class Character(
    val id: Int = 0,
    val name: String = "",
    val image: Image? = Image()
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
)