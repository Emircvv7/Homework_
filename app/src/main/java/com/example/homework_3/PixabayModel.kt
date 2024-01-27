package com.example.homework_3

data class PixabayModel(
    var hits: ArrayList<ImageModel>
)

class ImageModel(
    var largeImageURL: String,
    var likes: Int
)


