package app.purple.mvp.presenters

import app.purple.models.TodoList

interface TodoListPresenter {
    fun loadUI(todoList: TodoList)
    fun fetchTodoList(id: Long)
    fun removeTodoList(todoList: TodoList?)
}