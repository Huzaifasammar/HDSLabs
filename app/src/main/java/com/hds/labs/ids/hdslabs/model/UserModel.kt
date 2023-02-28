package com.hds.labs.ids.hdslabs.model

import com.google.gson.annotations.SerializedName


class UserModel {
    @SerializedName("code"    ) var code    : String? = null
    @SerializedName("message" ) var message : String? = null
    @SerializedName("data"    ) var data    : LogInData?   = LogInData()
}