package app.appk.mvp.models

import app.appk.models.TodoList

interface MainModel {
    fun findTodoLists(callback: (MutableList<TodoList>?) -> Unit)
}