package com.example.amphsesviewer.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AlbumWithImageIds(
    @Embedded val album: AlbumSM,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "imageId",
        entity = ImageSM::class,
        associateBy = Junction(
            value = ImageAlbumCrossRef::class,
            parentColumn = "albumId",
            entityColumn = "imageId"
        )
    )
    val images: List<ImageSM>
)