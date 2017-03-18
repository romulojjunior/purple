package app.appk.mvp.views

import app.appk.models.TodoItem
import app.appk.models.TodoList

interface TodoListView {
    fun loadTodoItemsRecyclerView(todoList: TodoList)
    fun showTodoItemFormDialog()
    fun addNewItemToTodoItemsRecyclerView(todoItem: TodoItem)
}