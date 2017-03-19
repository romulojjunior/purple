package app.appk.mvp.models

import app.appk.models.TodoList

class MainActivityModel : MainModel {

    override
    fun findTodoLists(callback: (MutableList<TodoList>?) -> Unit) {
        callback(TodoList.findAll()!!)
    }
}