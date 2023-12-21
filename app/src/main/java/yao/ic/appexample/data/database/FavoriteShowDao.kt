package yao.ic.appexample.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yao.ic.appexample.data.database.entity.FavoriteShow

@Dao
interface FavoriteShowDao {
    @Query("SELECT * FROM favorite_shows ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteShow>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteShow: FavoriteShow)
    @Delete
    suspend fun deleteFavorite(favoriteShow: FavoriteShow)

    @Query("SELECT EXISTS(SELECT * FROM favorite_shows WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
}