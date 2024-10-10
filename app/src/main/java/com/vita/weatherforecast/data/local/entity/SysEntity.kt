package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.Sys
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Sys")
data class SysEntity(
    val pod: String?
): Parcelable {
    @Ignore
    constructor(sys: Sys?) : this(
        pod = sys?.pod
    )
}