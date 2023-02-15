package com.markvtls.artstation.data.dto

data class Data(
    val id: String,
    val images: Images,
    val import_datetime: String,
    val trending_datetime: String,
    val type: String
)