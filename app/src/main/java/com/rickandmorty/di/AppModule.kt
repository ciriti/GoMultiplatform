package com.rickandmorty.di

import com.rickandmorty.kmp.repository.CharactersRepoMp
import com.rickandmorty.kmp.ServiceLocator
import com.rickandmorty.kmp.repository.create
import com.rickandmorty.presentation.CharactersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Carmelo Iriti
 */

val appModule = module {

    // use-case mp
    single { ServiceLocator.uc }


    single { CharactersRepoMp.create() }

    // ViewModel
    viewModel { CharactersViewModel(useCaseMp = get()) }

}