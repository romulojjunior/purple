package app.purple.mvp.presenters

import android.support.v4.app.Fragment
import app.purple.mvp.models.MainActivityModel
import app.purple.mvp.views.MainView
import app.purple.mvp.views.fragments.TodoListFragment

class MainActivityPresenter(
        var mainView: MainView,
        var mainActivityModel: MainActivityModel = MainActivityModel()): MainPresenter {

    override
    fun loadUI() {
        fetchTodoLists()
    }

    override
    fun fetchTodoLists() {
        mainActivityModel.findTodoLists { item ->
            val fragments: List<Fragment>? = item?.map { todoList -> TodoListFragment.newInstance(todoList.id) }
            mainView.loadViewPager(fragments)
        }
    }
}