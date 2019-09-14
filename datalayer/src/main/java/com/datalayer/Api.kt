package com.datalayer

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

  @GET(BuildConfig.URL + "character/?")
  fun getCharacters(@Query("page") page: Int): Deferred<Response<CharactersResponse>>

}