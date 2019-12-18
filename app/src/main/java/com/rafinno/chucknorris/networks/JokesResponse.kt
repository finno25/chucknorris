package com.rafinno.chucknorris.networks

data class Jokes(
    val categories: List<String>,
    val created_at: String,
    val icon_url: String,
    val id: String,
    val updated_at: String,
    val url: String,
    val value: String
)

data class SearchJokes(
    val total: Int,
    val result: List<Jokes>
)