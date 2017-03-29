package app.purple.mvp.views

import android.support.v4.app.Fragment
import app.purple.models.TodoList

interface MainView {
    fun loadViewPager(fragments: List<Fragment>?)
    fun showMessage(message: String)
    fun showTodoListFormDialog()

    // Callbacks
    fun onChangeNewTodoLists()
}