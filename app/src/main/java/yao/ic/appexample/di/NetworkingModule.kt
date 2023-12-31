package yao.ic.appexample.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import yao.ic.appexample.data.repository.TVMazeRepository
import yao.ic.appexample.network.TVMazeApiService
import yao.ic.appexample.network.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            )
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesTvMazeApiService(retrofit: Retrofit): TVMazeApiService {
        return retrofit.create(TVMazeApiService::class.java)
    }
}