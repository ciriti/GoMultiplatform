package com.datalayer.net

import com.datalayer.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkClient {

  @GET(BuildConfig.URL + "character/?")
  fun fetchCharacters(@Query("page") page: Int): Deferred<Response<CharactersResponse>>

}