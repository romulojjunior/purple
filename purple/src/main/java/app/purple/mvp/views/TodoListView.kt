package app.purple.mvp.views

import android.content.Context
import app.purple.models.TodoItem
import app.purple.models.TodoList
import io.reactivex.disposables.CompositeDisposable

interface TodoListView {
    val compositeDisposable: CompositeDisposable
    fun onFetchTodoList(todoList: TodoList?)
    fun onLoadAddItemButton()
    fun onLoadTodoItemsRecyclerView(todoList: TodoList)
    fun onAddNewItemToTodoItemsRecyclerView(todoItem: TodoItem)
    fun getContext(): Context?
    fun onShowMessage(message: String)
    fun onShowTodoItemFormDialog(item: TodoItem?)
    fun onRemoveTodoList()
}