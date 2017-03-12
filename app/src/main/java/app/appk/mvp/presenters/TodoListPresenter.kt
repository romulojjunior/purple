package app.appk.mvp.presenters

import app.appk.models.TodoList

interface TodoListPresenter {
    fun loadUI(todoList: TodoList)
    fun fetchTodoList(id: Long, callback: (TodoList?) -> Unit)
}