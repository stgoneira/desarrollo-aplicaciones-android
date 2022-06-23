package cl.aiep.android.anchorbooks.di

import cl.aiep.android.anchorbooks.service.LibroAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun baseUrl():String {
        return "https://my-json-server.typicode.com/Himuravidal/anchorBooks/"
    }

    @Provides
    @Singleton
    fun retrofit(baseUrl:String):Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun libroAPI(retrofit:Retrofit):LibroAPI {
        return retrofit.create(LibroAPI::class.java)
    }
}