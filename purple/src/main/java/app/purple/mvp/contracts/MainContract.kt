package app.purple.mvp.contracts

import android.support.v4.app.Fragment
import app.purple.models.TodoList
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface MainContract {

    interface View {
        val compositeDisposable: CompositeDisposable
        fun onLoadViewPager(fragments: List<Fragment>?)
        fun onChangeNewTodoLists()
        fun onShowTodoListFormDialog()
        fun onShowMessage(message: String)
    }

    interface Presenter {
        fun loadUI()
        fun fetchTodoLists()
    }

    interface Model {
        fun findTodoLists() : Observable<MutableList<TodoList>?>
    }
}