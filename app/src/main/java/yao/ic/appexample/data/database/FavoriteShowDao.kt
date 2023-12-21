package yao.ic.appexample.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowDao {
    @Query("SELECT * FROM favorite_shows ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteShow>>
}