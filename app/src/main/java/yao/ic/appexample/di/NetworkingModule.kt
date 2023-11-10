package yao.ic.appexample.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import yao.ic.appexample.network.BASE_URL
import yao.ic.appexample.network.AmphibiansApiService
import yao.ic.appexample.data.repository.AmphibiansRepository

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun providesAmphibiansApiService(retrofit: Retrofit): AmphibiansApiService {
        return retrofit.create(AmphibiansApiService::class.java)
    }

    @Provides
    fun providesAmphibiansRepository(api: AmphibiansApiService): AmphibiansRepository {
        return AmphibiansRepository(api)
    }

}