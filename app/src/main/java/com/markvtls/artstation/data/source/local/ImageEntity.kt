package com.markvtls.artstation.data.source.local


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ImageEntity: RealmObject {
    @PrimaryKey
    var id: String = "1"
    var url: String = ""
    var isFavorite: Boolean? = false
}