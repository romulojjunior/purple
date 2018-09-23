package app.purple.features.home.presenters

import android.support.v4.app.Fragment
import app.purple.models.TodoList
import app.purple.features.home.contracts.MainContract
import app.purple.features.home.models.MainActivityModel
import app.purple.features.todoList.views.TodoListFragment

class MainActivityPresenter(
        var mainView: MainContract.View,
        var mainActivityModel: MainContract.Model = MainActivityModel()) :
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