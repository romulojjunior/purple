package app.purple.mvp.presenters

import android.support.v4.app.Fragment
import app.purple.models.TodoList
import app.purple.mvp.contracts.MainContract
import app.purple.mvp.models.MainActivityModel
import app.purple.mvp.views.fragments.TodoListFragment

class MainActivityPresenter(
        var mainView: MainContract.View,
        var mainActivityModel: MainActivityModel = MainActivityModel() ) :
        MainContract.Presenter{

    override
    fun loadUI() {
        fetchTodoLists()
    }

    override
    fun fetchTodoLists() {
        val observable =  mainActivityModel.findTodoLists()

        mainView.compositeDisposable.add(
                observable.subscribe { todoList: MutableList<TodoList>? ->
                        val fragments: List<Fragment>? = todoList?.map { it ->
                        TodoListFragment.newInstance(it.id)
                    }
                mainView.onLoadViewPager(fragments)
                }
        )
    }
}