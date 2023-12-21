package yao.ic.appexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteShow::class], version = 1, exportSchema = false)
abstract class TVMazeDatabase : RoomDatabase() {
    abstract fun favoriteShowDao(): FavoriteShowDao
}