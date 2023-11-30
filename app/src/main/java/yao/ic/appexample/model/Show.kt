package yao.ic.appexample.model

import com.squareup.moshi.Json

data class Show(
    val id: Int = 0,
    val name: String = "",
    val genres: List<String> = emptyList(),
    val status: String = "",
    val rating : Rating = Rating(),
    val image: Image? = Image(),
    @Json(name = "summary") val description: String? = "",
    val premiered: String = "",
    val language: String = "",
    val country: Country = Country()
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

data class Country(
    val name: String = "",
    val code: String = "",
    val timezone: String = ""
)