package app.appk.mvp.views

import android.content.Context
import app.appk.models.TodoItem
import app.appk.models.TodoList

interface TodoListView {
    fun loadTodoItemsRecyclerView(todoList: TodoList)
    fun addNewItemToTodoItemsRecyclerView(todoItem: TodoItem)
    fun getContext(): Context

    // Shows
    fun showMessage(message: String)
    fun showTodoItemFormDialog(item: TodoItem?)

    // Calbacks
    fun onRemoveTodoList()

    fun loadAddItemButton()
}