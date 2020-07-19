package com.nizar.searchusergithub

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nizar.searchusergithub.model.Item
import com.nizar.searchusergithub.mvp.SearchAdapter
import com.nizar.searchusergithub.mvp.SearchPresenter
import com.nizar.searchusergithub.mvp.SearchView
import com.nizar.searchusergithub.util.EndlessRecyclerViewScrollListener

class MainActivity : AppCompatActivity(), SearchView.InitView {
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var searchPresenter: SearchPresenter? = null
    private var emptyView: RelativeLayout? = null
    private var errorTitle: TextView? = null
    private var errorMessage: TextView? = null
    private var searchView: EditText? = null
    private var searchAdapter: SearchAdapter? = null
    private var users: MutableList<Item> = mutableListOf()
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        searchPresenter = SearchPresenter(this)
    }

    private fun init() {
        emptyView = findViewById(R.id.empty_view)
        errorTitle = findViewById(R.id.errorTitle)
        errorMessage = findViewById(R.id.errorMessage)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recycler)
        searchView = findViewById(R.id.search_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        searchAdapter = SearchAdapter(users, this)
        recyclerView?.adapter = searchAdapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                searchPresenter?.loadMoreUserList(searchView?.text?.toString())
            }
        }

        recyclerView?.addOnScrollListener(scrollListener)

        //searchView.isIconified = false
        searchView?.clearFocus()
        searchView?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v?.text.toString().isNotEmpty()) {
                    scrollListener.resetState()
                    searchPresenter?.getUserList(v?.text.toString())
                } else {
                    users.clear()
                    searchAdapter?.notifyDataSetChanged()
                }

            }
            false
        }

        searchView?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                print("length ="+s)
                Log.wtf("count = ", count.toString())
                if(count == 0) {
                    searchPresenter?.page = 1
                    users.clear()
                    searchAdapter?.notifyDataSetChanged()
                } else if(count>0){
                    searchPresenter?.page = 1
                    scrollListener.resetState()
                    searchPresenter?.getUserList(s.toString())
                }
            }
        })

    }

    override fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
        errorView(View.GONE, "", "")
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun userList(items: List<Item?>?) {
        if(items!=null) {
            users.clear()
            items.listIterator().forEach {
                it?.let { it1 -> users.add(it1) }
            }
        }
        searchAdapter?.notifyDataSetChanged()
    }

    override fun addMoreUserList(items: List<Item?>?) {
        items?.listIterator()?.forEach {
            it?.let { it1 -> users.add(it1) }
        }
        searchAdapter?.notifyDataSetChanged()
    }

    override fun userListFailure(errorMessage: String?, keyword: String?) {
        users.clear()
        searchPresenter?.page = 1
        errorView(View.VISIBLE, errorMessage ?: "", keyword ?: "")
    }

    private fun errorView(
        visibility: Int,
        title: String,
        message: String
    ) {
        emptyView!!.visibility = visibility
        errorTitle!!.text = title
        errorMessage!!.text = message
    }
}
