package com.example.tast1.model

import com.google.gson.annotations.SerializedName

data class Facility(
    @SerializedName("facility_id")
    val facilityId : String,
    @SerializedName("name")
    val facilityName : String,
    @SerializedName("options")
    val facilities : List<FacilityItem>
)

data class FacilityItem(
    @SerializedName("id")
    val itemId : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("icon")
    val icon : String
)