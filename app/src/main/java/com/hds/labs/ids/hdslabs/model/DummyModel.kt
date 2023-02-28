package com.hds.labs.ids.hdslabs.model

import com.google.gson.annotations.SerializedName

class DummyModel (


    @SerializedName("code"    ) var code    : String?         = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)


    data class Data (

        @SerializedName("id"           ) var id          : Int?    = null,
        @SerializedName("name"         ) var name        : String? = null,
        @SerializedName("designation"  ) var designation : String? = null,
        @SerializedName("img"          ) var img         : String? = null,
        @SerializedName("education"    ) var education   : String? = null,
        @SerializedName("fb_link"      ) var fbLink      : String? = null,
        @SerializedName("twitter_link" ) var twitterLink : String? = null,
        @SerializedName("youtube_link" ) var youtubeLink : String? = null,
        @SerializedName("insta_link"   ) var instaLink   : String? = null,
        @SerializedName("created_at"   ) var createdAt   : String? = null,
        @SerializedName("updated_at"   ) var updatedAt   : String? = null

    )
