package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.Clouds
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Clouds")
data class CloudsEntity(
    @ColumnInfo(name = "all")
    val all: Int?
): Parcelable {
    @Ignore
    constructor(clouds: Clouds?) : this(
        all = clouds?.all ?: 0
    )
}