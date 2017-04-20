package app.purple.mvp.views

import android.support.v4.app.Fragment
import app.purple.models.TodoList

interface MainView {
    fun onLoadViewPager(fragments: List<Fragment>?)
    fun onChangeNewTodoLists()
    fun onShowTodoListFormDialog()
    fun onShowMessage(message: String)
}