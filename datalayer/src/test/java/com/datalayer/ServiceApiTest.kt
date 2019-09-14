package com.datalayer

import com.datalayer.net.NetworkClient
import com.datalayer.repository.CharactersRepo
import com.datalayer.repository.create
import com.datalayer.util.Logger
import com.datalayer.util.create
import com.datalayer.util.createCoroutineAdapter
import com.util.test.runBlockingUnit
import org.junit.Test
import retrofit2.Retrofit

class ServiceApiTest {

    @Test
    fun net() = runBlockingUnit {
        val retrofit = Retrofit.Builder().createCoroutineAdapter<NetworkClient>(BuildConfig.URL)
        val characters = retrofit.fetchCharacters(1).await()
        println(characters)
    }

    @Test
    fun datasource() = runBlockingUnit {
        val retrofit = Retrofit.Builder().createCoroutineAdapter<NetworkClient>(BuildConfig.URL)
        val datasource = CharactersRepo.create(retrofit, Logger.create())
        val characters = datasource.getCharacters(1)
        println(characters)
    }

}