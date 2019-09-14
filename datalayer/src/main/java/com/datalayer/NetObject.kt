package com.datalayer


data class Result(
    var id: Int = -1,
    var name: String = "",
    var status: String = "",
    var image: String = ""
)

data class CharactersResponse(
    var results: List<Result> = emptyList()
)