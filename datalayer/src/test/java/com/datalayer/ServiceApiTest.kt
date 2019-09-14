package com.datalayer

import com.util.test.runBlockingUnit
import org.junit.Test
import retrofit2.Retrofit

class ServiceApiTest {

    @Test
    fun net() = runBlockingUnit {
        val retrofit = Retrofit.Builder().createCoroutineAdapter<Api>(BuildConfig.URL)
        val characters = retrofit.getCharacters(1).await()
        println(characters)
    }

    @Test
    fun datasource() = runBlockingUnit {
        val retrofit = Retrofit.Builder().createCoroutineAdapter<Api>(BuildConfig.URL)
        val datasource = CharactersDataSource.create(retrofit, Logger.create())
        val characters = datasource.getCharacters(1)
        println(characters)
    }

}