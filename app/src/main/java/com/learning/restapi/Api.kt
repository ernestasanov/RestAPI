package com.learning.restapi

data class Api(
    val id: Int,
    val name: String,
    val htmlUrl: String,
    val description: String,
    val language: String,
    val stargazersCount: Int
)