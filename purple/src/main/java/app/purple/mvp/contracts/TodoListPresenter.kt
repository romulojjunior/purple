package app.purple.mvp.contracts

import app.purple.models.TodoList

interface TodoListPresenter {
    fun loadUI(todoList: TodoList)
    fun fetchTodoList(id: Long)
    fun removeTodoList(todoList: TodoList?)
}