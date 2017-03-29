package app.purple.mvp.views

import android.content.Context
import app.purple.models.TodoItem
import app.purple.models.TodoList

interface TodoListView {
    fun loadAddItemButton()
    fun loadTodoItemsRecyclerView(todoList: TodoList)
    fun addNewItemToTodoItemsRecyclerView(todoItem: TodoItem)
    fun getContext(): Context
    fun showMessage(message: String)
    fun showTodoItemFormDialog(item: TodoItem?)

    // Calbacks
    fun onRemoveTodoList()
}