package com.hds.labs.ids.hdslabs.model

import com.google.gson.annotations.SerializedName

data class LogInData (
    @SerializedName("id"                  ) var id                : Int?    = null,
    @SerializedName("employee_id"         ) var employeeId        : String? = null,
    @SerializedName("name"                ) var name              : String? = null,
    @SerializedName("email"               ) var email             : String? = null,
    @SerializedName("doj"                 ) var doj               : String? = null,
    @SerializedName("age"                 ) var age               : String? = null,
    @SerializedName("designation"         ) var designation       : String? = null,
    @SerializedName("address"             ) var address           : String? = null,
    @SerializedName("gender"              ) var gender            : String? = null,
    @SerializedName("phone_number"        ) var phoneNumber       : String? = null,
    @SerializedName("device_token"        ) var deviceToken       : String? = null,
    @SerializedName("check_in"            ) var checkIn           : String? = null,
    @SerializedName("check_in_time"       ) var checkInTime       : String? = null,
    @SerializedName("check_in_latitude"   ) var checkInLatitude   : Double? = null,
    @SerializedName("check_in_longitude"  ) var checkInLongitude  : Double? = null,
    @SerializedName("check_out_latitude"  ) var checkOutLatitude  : Double? = null,
    @SerializedName("check_out_longitude" ) var checkOutLongitude : Double? = null,
    @SerializedName("check_out_time"      ) var checkOutTime      : String? = null
)
