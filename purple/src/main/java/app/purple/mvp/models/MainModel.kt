package app.purple.mvp.models

import app.purple.models.TodoList

interface MainModel {
    fun findTodoLists(callback: (MutableList<TodoList>?) -> Unit)
}