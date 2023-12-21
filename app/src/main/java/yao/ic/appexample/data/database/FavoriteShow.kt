package yao.ic.appexample.data.database

import androidx.room.Entity
import yao.ic.appexample.util.FAVORITE_SHOWS_TABLE

@Entity(tableName = FAVORITE_SHOWS_TABLE, primaryKeys = ["id"])
data class FavoriteShow(
    val id: Int,
    val name: String,
    val image: String,
    val rate: String,
    val genres: String,
)
