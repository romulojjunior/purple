package app.appk.mvp.views

import android.support.v4.app.Fragment

interface MainView {
    fun loadViewPager(fragments: List<Fragment>)
    fun showMessage(message: String)
}