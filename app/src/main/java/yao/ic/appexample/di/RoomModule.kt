package yao.ic.appexample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import yao.ic.appexample.data.database.FavoriteShowDao
import yao.ic.appexample.data.database.TVMazeDatabase
import yao.ic.appexample.data.repository.TVMazeRepository
import yao.ic.appexample.network.TVMazeApiService
import yao.ic.appexample.util.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideTVMazeDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        TVMazeDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideFavoriteShowDao(
        db: TVMazeDatabase
    ) = db.favoriteShowDao()

    @Provides
    @Singleton
    fun provideTVMazeRepository(
        dao: FavoriteShowDao,
        api: TVMazeApiService
    ) = TVMazeRepository(api, dao)
}