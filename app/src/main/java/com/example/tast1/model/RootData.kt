package com.example.tast1.model

import com.google.gson.annotations.SerializedName

data class RootData(
    @SerializedName("facilities")
    val facilities : List<Facility>,
    @SerializedName("exclusions")
    val exclusions : List<List<ExclusionItem>>
)