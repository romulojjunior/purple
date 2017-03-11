package app.appk.mvp.views

import app.appk.models.TodoList

interface TodoListView {
    fun loadUI(todoListId: Long)
    fun loadTodoItemsRecyclerView(todoList: TodoList)
}