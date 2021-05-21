package com.example.tast1.model

import com.google.gson.annotations.SerializedName

data class ExclusionItem(
    @SerializedName("facility_id")
    val facilityId : String,
    @SerializedName("options_id")
    val optionsId : String
)