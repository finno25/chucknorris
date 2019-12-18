package com.rafinno.chucknorris.views.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafinno.chucknorris.R
import com.rafinno.chucknorris.networks.BaseNetwork
import com.rafinno.chucknorris.views.adapters.CategoryAdapter
import com.rafinno.chucknorris.views.customs.InfoView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME"
const val EXTRA_QUERY = "EXTRA_QUERY"
class MainActivity : AppCompatActivity() {

    private var categoryAdapter: CategoryAdapter? = null

    private val categoryAdapterListener = object : CategoryAdapter.Listener {
        override fun onItemClicked(categoryName: String) {
            val intent = Intent(this@MainActivity, JokesActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        categoryAdapter = CategoryAdapter(categoryAdapterListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        listView.setLayoutManager(linearLayoutManager)
        listView.setAdapter(categoryAdapter)

        infoViewMain.setListener(object : InfoView.Listener{
            override fun onBtnTryAgainClicked() {
                infoViewMain.hideError()
                getCategories()
            }
        })

        getCategories()
    }

    private fun getCategories() {
        infoViewMain.showLoading()
        BaseNetwork.instance.chuckNorrisApi.categories.enqueue(object : Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                infoViewMain.showError(t.message)
                infoViewMain.hideLoading()
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val categoryList = response.body()
                    if(categoryList == null) {
                        infoViewMain.showEmptyData()
                    } else {
                        if(categoryList.isEmpty()) {
                            infoViewMain.showEmptyData()
                        } else {
                            updateViews(categoryList)
                        }
                    }
                } else {
                    infoViewMain.showError(response.errorBody().toString())
                }
                infoViewMain.hideLoading()
            }
        })
    }

    private fun updateViews(categories: List<String>?) {
        categoryAdapter?.setData(categories)
        categoryAdapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        search.setSearchableInfo(manager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if(it.length >= 3) {
                        val intent = Intent(this@MainActivity, SearchActivity::class.java)
                        intent.putExtra(EXTRA_QUERY, it)
                        startActivity(intent)
                        return true
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }
}
