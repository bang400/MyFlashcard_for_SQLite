package com.yamato.myflashcard_for_sqlite.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val commentary: String,
    val right: Int,
    val wrong: Int,
    val created: Long,
    val modified: Long
): Parcelable