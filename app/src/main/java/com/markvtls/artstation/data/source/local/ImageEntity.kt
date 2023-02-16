package com.markvtls.artstation.data.source.local


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ImageEntity: RealmObject {
    var id: String = ""
    var url: String = ""
    var title: String = ""
    var isMain: Boolean = false
    var isFavorite: Boolean = false
}