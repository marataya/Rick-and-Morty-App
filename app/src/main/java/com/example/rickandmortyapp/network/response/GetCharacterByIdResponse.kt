package com.example.rickandmortyapp.network.response


import com.squareup.moshi.Json

data class Origin(@Json(name = "name")
                  val name: String = "",
                  @Json(name = "url")
                  val url: String = "")


data class Location(@Json(name = "name")
                    val name: String = "",
                    @Json(name = "url")
                    val url: String = "")

data class GetCharacterByIdResponse(@Json(name = "image")
                                    val image: String = "",
                                    @Json(name = "gender")
                                    val gender: String = "",
                                    @Json(name = "species")
                                    val species: String = "",
                                    @Json(name = "created")
                                    val created: String = "",
                                    @Json(name = "origin")
                                    val origin: Origin,
                                    @Json(name = "name")
                                    val name: String = "",
                                    @Json(name = "location")
                                    val location: Location,
                                    @Json(name = "episode")
                                    val episode: List<String>?,
                                    @Json(name = "id")
                                    val id: Int = 0,
                                    @Json(name = "type")
                                    val type: String = "",
                                    @Json(name = "url")
                                    val url: String = "",
                                    @Json(name = "status")
                                    val status: String = "")



