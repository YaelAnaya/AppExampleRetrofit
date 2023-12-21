package yao.ic.appexample.data.database.entity

import android.provider.Telephony.Mms.Rate
import androidx.room.Entity
import yao.ic.appexample.data.model.Image
import yao.ic.appexample.data.model.Rating
import yao.ic.appexample.util.FAVORITE_SHOWS_TABLE

@Entity(tableName = FAVORITE_SHOWS_TABLE, primaryKeys = ["id"])
data class FavoriteShow(
    val id: Int,
    val name: String,
    val image: String,
    val rate: String,
    val genres: String,
)

fun FavoriteShow.toShow() = yao.ic.appexample.data.model.Show(
    id = id,
    name = name,
    image = Image(medium = image, original = image),
    rating = Rating(average = rate.toDouble()),
    genres = genres.split(",")
)