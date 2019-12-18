package com.rafinno.chucknorris.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rafinno.chucknorris.R
import com.rafinno.chucknorris.commons.Utilities
import com.rafinno.chucknorris.networks.BaseNetwork
import com.rafinno.chucknorris.networks.Jokes
import com.rafinno.chucknorris.views.customs.InfoView
import kotlinx.android.synthetic.main.activity_jokes.*
import kotlinx.android.synthetic.main.activity_jokes.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JokesActivity : AppCompatActivity() {
    private var selectedCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        itemSearch.visibility = View.GONE

        selectedCategory = intent.getStringExtra(EXTRA_CATEGORY_NAME)

        infoViewJokes.setListener(object : InfoView.Listener{
            override fun onBtnTryAgainClicked() {
                infoViewJokes.hideError()
                getRandomJokes(selectedCategory)
            }
        })

        getRandomJokes(selectedCategory)
    }

    private fun getRandomJokes(categoryName: String) {
        infoViewJokes.showLoading()
        BaseNetwork.instance.chuckNorrisApi.getRandomJokesFromCategory(categoryName).enqueue(object : Callback<Jokes> {
            override fun onFailure(call: Call<Jokes>, t: Throwable) {
                infoViewJokes.showError(t.message)
                infoViewJokes.hideLoading()
            }

            override fun onResponse(call: Call<Jokes>, response: Response<Jokes>) {
                if (response.isSuccessful) {
                    val jokes = response.body()
                    if(jokes == null) {
                        infoViewJokes.showEmptyData()
                    } else {
                        updateViews(jokes)
                    }
                } else {
                    infoViewJokes.showError(response.errorBody().toString())
                }
                infoViewJokes.hideLoading()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateViews(jokes: Jokes?) {
        jokes?.let {
            itemSearch.visibility = View.VISIBLE
            jokesValue.text = it.value
            Glide.with(this)
                .load(it.icon_url)
                .into(jokesIcon)
            jokesUpdated.text = "updated at ${Utilities.convertDatetime(it.updated_at, "yyyy-MM-dd hh:mm:ss.SSS", "dd MMM yyyy hh:mm a")}"
        }
    }
}