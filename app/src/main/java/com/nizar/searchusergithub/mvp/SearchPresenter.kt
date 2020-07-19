package com.nizar.searchusergithub.mvp

import androidx.annotation.NonNull
import com.nizar.searchusergithub.api.ApiClient
import com.nizar.searchusergithub.api.ApiInterface
import com.nizar.searchusergithub.model.Item
import com.nizar.searchusergithub.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.nizar.searchusergithub.mvp.SearchView.InitView
import com.nizar.searchusergithub.mvp.SearchView.GetUsers

/**
 * Created by Nizar Assegaf on 19,July,2020
 */

class SearchPresenter(private val initView: InitView) : GetUsers {

    var key = ""
    var page = 1
    var onRequest = false;

    override fun getUserList(keyword: String?) {
        initView.showLoading()
        onRequest  = true
        val apiInterface: ApiInterface = ApiClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getUsers(keyword, 1, 20)?.enqueue(object : Callback<Users?> {
            override fun onResponse(call: Call<Users?>, response: Response<Users?>) {
                initView.hideLoading()
                key = keyword?:""
                val totalCount: Int? = response.body()?.getTotalCount()
                if (!response.isSuccessful || response.body()?.getItems() == null || totalCount == 0) {
                    initView.userListFailure(
                        "No Result for '$keyword'",
                        "Try Searching for Other Users"
                    )
                }else {
                    initView.userList(response.body()?.getItems())
                }
                onRequest  = false
            }

            override fun onFailure(call: Call<Users?>, t: Throwable) {
                initView.userListFailure("Error Loading For '$keyword'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
                onRequest  = false
            }
        })
    }

    override fun loadMoreUserList(keyword: String?) {
        if(!onRequest) {
            onRequest=true
            initView.showLoading()
            page++
            val apiInterface: ApiInterface =
                ApiClient.getApiClient()!!.create(ApiInterface::class.java)
            apiInterface.getUsers(keyword, page, 20)?.enqueue(object : Callback<Users?> {
                override fun onResponse(call: Call<Users?>, response: Response<Users?>) {
                    initView.hideLoading()
                    val totalCount: Int? = response.body()?.getTotalCount()
                    if (response.isSuccessful && response.body()?.getItems() != null && totalCount != 0) {
                        initView.addMoreUserList(response.body()?.getItems())
                    }
                    onRequest=false
                }

                override fun onFailure(call: Call<Users?>, t: Throwable) {
                    initView.userListFailure("Error Loading For '$key'", t.toString())
                    initView.hideLoading()
                    t.printStackTrace()
                    onRequest=false
                }
            })
        }
    }

}