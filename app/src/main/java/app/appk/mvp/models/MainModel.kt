package app.appk.mvp.models

import app.appk.models.TodoList

interface MainModel {
    fun todoLists(callback: (MutableList<TodoList>?) -> Unit)
}