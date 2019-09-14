package com.rickandmorty.di

import com.datalayer.net.NetworkClient
import com.datalayer.repository.CharactersRepo
import com.datalayer.repository.create
import com.datalayer.util.Logger
import com.datalayer.util.create
import com.datalayer.util.createCoroutineAdapter
import com.rickandmorty.BuildConfig
import com.rickandmorty.presentation.CharactersUsecase
import com.rickandmorty.presentation.CharactersViewModel
import com.rickandmorty.presentation.create
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Carmelo Iriti
 */

val appModule = module {

    // ViewModel
    viewModel { CharactersViewModel(charactersRepo = get()) }

    // Usecase
    single { CharactersUsecase.create(charactersRepo = get()) }

    // Datasource
    single { CharactersRepo.create(networkClient = get(), logger = get()) }

    // Retrofit adapter
    single { Retrofit.Builder().createCoroutineAdapter<NetworkClient>(BuildConfig.URL) }

    // Logger
    single { Logger.create() }

}