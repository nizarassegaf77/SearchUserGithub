package com.nizar.searchusergithub.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nizar Assegaf on 19,July,2020
 */

class Users {
    @SerializedName("items")
    @Expose
    private var items: List<Item>? = null
    @SerializedName("total_count")
    @Expose
    private var totalCount = 0

    fun getItems(): List<Item>? {
        return items
    }

    fun setItems(items: List<Item>?) {
        this.items = items
    }

    fun getTotalCount(): Int {
        return totalCount
    }

    fun setTotalCount(totalCount: Int) {
        this.totalCount = totalCount
    }
}
