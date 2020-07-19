package com.nizar.searchusergithub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Nizar Assegaf on 19,July,2020
 */


class Item {
    @SerializedName("login")
    var name: String? = null
    @SerializedName("avatar_url")
    var avatar: String? = null
    @SerializedName("url")
    var url: String? = null

}
