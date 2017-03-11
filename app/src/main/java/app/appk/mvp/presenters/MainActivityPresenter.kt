package app.appk.mvp.presenters

import android.support.v4.app.Fragment
import app.appk.mvp.models.MainActivityModel
import app.appk.mvp.views.MainView
import app.appk.mvp.views.fragments.TodoListFragment

class MainActivityPresenter(
        var mainView: MainView,
        var mainActivityModel: MainActivityModel = MainActivityModel()): MainPresenter {

    override
    fun loadUI() {
        fetchTodoLists()
    }

    override
    fun fetchTodoLists() {
        mainActivityModel.todoLists { item ->
            var fragments: List<Fragment> = item.map { todoList ->TodoListFragment.newInstance(todoList.id) }
            mainView.loadViewPager(fragments)
        }
    }
}