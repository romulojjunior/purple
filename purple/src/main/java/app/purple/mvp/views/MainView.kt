package app.purple.mvp.views

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

interface MainView {
    val compositeDisposable: CompositeDisposable
    fun onLoadViewPager(fragments: List<Fragment>?)
    fun onChangeNewTodoLists()
    fun onShowTodoListFormDialog()
    fun onShowMessage(message: String)
}