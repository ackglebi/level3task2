package com.example.studentportal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URL

@Parcelize
data class Portal (
    val title: String,
    val url: URL
) : Parcelable