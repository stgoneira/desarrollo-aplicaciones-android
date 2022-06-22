package cl.stgoneira.ejemploespresso.di

import cl.stgoneira.ejemploespresso.data.remote.LibroRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

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
    fun libroRemoteDataSource(retrofit: Retrofit):LibroRemote {
        return retrofit.create(LibroRemote::class.java)
    }

}