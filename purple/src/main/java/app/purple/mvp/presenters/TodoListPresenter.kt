package app.purple.mvp.presenters

import app.purple.models.TodoList

interface TodoListPresenter {
    fun loadUI(todoList: TodoList)
    fun fetchTodoList(id: Long, callback: (TodoList?) -> Unit)
    fun removeTodoList(todoList: TodoList?)
}