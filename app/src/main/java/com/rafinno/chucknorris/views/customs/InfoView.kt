package com.rafinno.chucknorris.views.customs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.rafinno.chucknorris.R
import kotlinx.android.synthetic.main.view_info.view.*

class InfoView : RelativeLayout {
    constructor(context: Context?) : super(context) {
        initView(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initView(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private var listener: Listener? = null

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_info, this)
        btnTryAgain.setOnClickListener(object : OnClickListener{
            override fun onClick(p0: View?) {
                listener?.let {
                    it.onBtnTryAgainClicked()
                }
            }
        })
        hideLoading()
        hideEmptyData()
        hideError()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    fun showError(msg: String?) {
        tvErrorMsg.text = msg
        errorView.visibility = View.VISIBLE
    }

    fun hideError() {
        errorView.visibility = View.GONE
    }

    fun showEmptyData() {
        emptyResultView.visibility = View.VISIBLE
    }

    fun hideEmptyData() {
        emptyResultView.visibility = View.GONE
    }

    interface Listener {
        fun onBtnTryAgainClicked()
    }
}