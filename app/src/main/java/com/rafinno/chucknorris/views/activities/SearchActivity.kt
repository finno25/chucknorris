package com.rafinno.chucknorris.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafinno.chucknorris.R
import com.rafinno.chucknorris.networks.BaseNetwork
import com.rafinno.chucknorris.networks.Jokes
import com.rafinno.chucknorris.networks.SearchJokes
import com.rafinno.chucknorris.views.adapters.CategoryAdapter
import com.rafinno.chucknorris.views.adapters.SearchAdapter
import com.rafinno.chucknorris.views.customs.InfoView
import kotlinx.android.synthetic.main.activity_jokes.*
import kotlinx.android.synthetic.main.activity_jokes.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private val searchAdapter = SearchAdapter()
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        listResult.setLayoutManager(linearLayoutManager)
        listResult.setAdapter(searchAdapter)

        query = intent.getStringExtra(EXTRA_QUERY)

        infoViewSearch.setListener(object : InfoView.Listener {
            override fun onBtnTryAgainClicked() {
                infoViewSearch.hideError()
                searchJokes(query)
            }
        })

        searchJokes(query)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun searchJokes(query: String) {
        infoViewSearch.showLoading()
        BaseNetwork.instance.chuckNorrisApi.getSearchJokes(query).enqueue(object :
            Callback<SearchJokes> {
            override fun onFailure(call: Call<SearchJokes>, t: Throwable) {
                infoViewSearch.showError(t.message)
                infoViewSearch.hideLoading()
            }

            override fun onResponse(call: Call<SearchJokes>, response: Response<SearchJokes>) {
                if (response.isSuccessful) {
                    val jokesList = response.body()?.result
                    if (jokesList == null) {
                        infoViewSearch.showEmptyData()
                    } else {
                        if (jokesList.isEmpty()) {
                            infoViewSearch.showEmptyData()
                        } else {
                            updateViews(jokesList)
                        }
                    }
                } else {
                    infoViewSearch.showError(response.errorBody().toString())
                }
                infoViewSearch.hideLoading()
            }
        })
    }

    private fun updateViews(jokes: List<Jokes>?) {
        searchAdapter.setData(jokes)
        searchAdapter.notifyDataSetChanged()
    }
}