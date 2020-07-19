package com.nizar.searchusergithub.mvp

import com.nizar.searchusergithub.model.Item

/**
 * Created by Nizar Assegaf on 19,July,2020
 */
interface SearchView {
    interface InitView {
        fun showLoading()
        fun hideLoading()
        fun userList(items: List<Item?>?)
        fun addMoreUserList(items: List<Item?>?)
        fun userListFailure(errorMessage: String?, keyword: String?)
    }

    interface GetUsers {
        fun getUserList(keyword: String?)
        fun loadMoreUserList(keyword: String?)
    }
}
