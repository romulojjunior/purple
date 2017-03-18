package app.appk.mvp.views

import android.support.v4.app.Fragment
import app.appk.models.TodoList

interface MainView {
    fun loadViewPager(fragments: List<Fragment>?)
    fun showMessage(message: String)
    fun onNewTodoListCreated(todoList: TodoList)
}